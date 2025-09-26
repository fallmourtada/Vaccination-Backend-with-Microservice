package com.gestionvaccination.auth_service1.config;

import java.io.IOException;

import com.gestionvaccination.auth_service1.service.CustomUserDetailService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtils jwtUtils;

    // 👉 Cette méthode est exécutée pour chaque requête HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 🔹 On récupère le header "Authorization" (celui qui contient normalement le
        // token JWT)
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 🔹 Si le header existe ET commence par "Bearer " → alors c'est un token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // on enlève "Bearer " pour garder uniquement le token
            username = jwtUtils.extractUsername(token); // on récupère le username depuis le token
        }

        // 🔹 Si on a bien un username ET que personne n'est encore authentifié dans le
        // contexte de sécurité
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // On charge les infos de l'utilisateur depuis la BDD
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

            // 🔹 On vérifie que le token est valide pour cet utilisateur
            if (jwtUtils.validateToken(token, userDetails)) {

                // On crée un objet d'authentification pour Spring Security
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, // utilisateur authentifié
                        null, // pas besoin du mot de passe (déjà validé)
                        userDetails.getAuthorities() // rôles/permissions de l'utilisateur
                );

                // On associe les détails de la requête (adresse IP, session, etc.)
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 🔹 On place l'objet d'authentification dans le SecurityContext
                // 👉 À partir de maintenant, Spring Security considère l'utilisateur comme
                // authentifié
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 🔹 On passe la main au filtre suivant dans la chaîne
        filterChain.doFilter(request, response);
    }
}
