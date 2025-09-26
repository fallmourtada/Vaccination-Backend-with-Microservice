package com.gestionvaccination.userservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collections;

/**
 * Filtre JWT pour le microservice 'user-service'.
 * Il intercepte les requêtes, valide le token JWT
 * généré par l'auth-service et place l'utilisateur dans le SecurityContext.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // La clé secrète doit être la même que dans auth-service
    @Value("${app.jwt-secret}")
    private String secretKey;

    /**
     * Logique principale du filtre : validation du token pour chaque requête.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Récupérer le token du header 'Authorization'
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // 2. Valider et extraire les informations (Claims)
            Claims claims = extractAllClaims(token);
            String username = claims.getSubject();

            // 3. Extraire le rôle (doit être cohérent avec l'auth-service: 'role' ou 'scope')
            String role = null;
            if (claims.get("role") != null) {
                role = claims.get("role", String.class);
            } else if (claims.get("scope") != null) {
                role = claims.get("scope", String.class);
            }

            // Assurer que le rôle a le préfixe Spring Security 'ROLE_'
            if (role != null && !role.startsWith("ROLE_")) {
                role = "ROLE_" + role;
            }

            // 4. Authentification si l'utilisateur n'est pas déjà dans le contexte
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Créer l'objet d'authentification pour Spring Security
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        // Utiliser le rôle extrait ou un rôle par défaut si manquant
                        Collections.singletonList(new SimpleGrantedAuthority(role != null ? role : "ROLE_USER"))
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (ExpiredJwtException e) {
            // Gérer les tokens expirés
            System.err.println("Token expiré: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expiré");
            return;
        } catch (Exception e) {
            // Gérer l'erreur de signature ou de parsing (résolution du 401)
            System.err.println("Erreur d'authentification (Signature/Parsing): " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Erreur d'authentification: " + e.getMessage());
            return;
        }

        // 5. Continuer la chaîne de filtres
        filterChain.doFilter(request, response);
    }

    /**
     * Extrait toutes les claims d'un token après validation de la signature.
     * @param token Le JWT à parser.
     * @return Les claims contenues dans le token.
     */
    private Claims extractAllClaims(String token) {
        SecretKey key = getSignKey();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Crée la clé secrète de validation à partir de la chaîne Base64 de la configuration.
     * @return La clé secrète pour JJWT.
     */
    private SecretKey getSignKey() {
        try {
            // Utiliser Decoders.BASE64 pour la compatibilité avec l'auth-service
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            // Lancer une erreur critique si la clé est mal configurée
            throw new RuntimeException("Problème critique avec la clé JWT : " + e.getMessage());
        }
    }
}
