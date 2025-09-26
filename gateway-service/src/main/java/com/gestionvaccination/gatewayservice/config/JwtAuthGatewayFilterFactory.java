package com.gestionvaccination.gatewayservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.List;

/**
 * Filtre de passerelle personnalisé pour valider les tokens JWT.
 * Ce filtre est appliqué aux routes sécurisées.
 *
 * ATTENTION: Le nom de la classe doit se terminer par 'GatewayFilterFactory' pour
 * que Spring Cloud Gateway la reconnaisse sous le nom court 'JwtAuth' dans la configuration.
 */
@RefreshScope
@Component
public class JwtAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthGatewayFilterFactory.Config> {

    @Value("${app.jwt-secret}")
    private String secretKey;

    // Remplacement du constructeur pour correspondre au nouveau nom de classe
    public JwtAuthGatewayFilterFactory() {
        super(Config.class);
    }

    // Liste des préfixes de chemins publics qui NE DOIVENT PAS être sécurisés par JWT
    public static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/api/auth/v1",                     // Route vers le service d'authentification
            "/api/v1/users/public",             // Exemple : Endpoint public dans USER-SERVICE
            "/api/v1/localities/all",           // Exemple : Endpoint public dans LOCATION-SERVICE (Keep-Alive)
            "/api/v1/localities/all-available"  // Exemple: Ajout d'une nouvelle route publique
            // AJOUTEZ ICI tous les autres chemins publics
    );


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 1. Vérifie si la requête est vers un endpoint sécurisé
            if (isSecured(request)) {

                // 2. Vérifie la présence du header d'autorisation
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return this.onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    return this.onError(exchange, "Invalid/Malformed Authorization Header", HttpStatus.UNAUTHORIZED);
                }

                String token = authHeader.substring(7);

                try {
                    // 3. Valide le token
                    validateToken(token);

                    // Optionnel : Ajouter les claims (rôles) à la requête pour les microservices
                    Claims claims = extractAllClaims(token);
                    addClaimsToRequest(request, claims);

                } catch (Exception e) {
                    // 4. Gère les erreurs de validation (signature, expiration)
                    System.err.println("JWT Validation Error: " + e.getMessage());
                    return this.onError(exchange, "JWT validation failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
                }
            }

            // 5. La requête est valide ou non sécurisée, passe au service de destination
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private void addClaimsToRequest(ServerHttpRequest request, Claims claims) {
        // Ajoute l'utilisateur et le rôle dans les headers pour les microservices
        request.mutate()
                .header("X-User-ID", claims.getSubject())
                .header("X-User-Role", claims.get("role", String.class))
                .build();
    }

    private boolean isSecured(ServerHttpRequest request) {
        final String path = request.getURI().getPath();

        // La requête est sécurisée s'il n'y a AUCUNE correspondance avec les chemins publics
        return PUBLIC_PATHS.stream()
                .noneMatch(path::startsWith);
    }

    // --- Logique d'extraction et de validation du JWT ---

    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            // Lancer une erreur critique si la clé est mal configurée
            throw new RuntimeException("Problème critique avec la clé JWT dans la Gateway : " + e.getMessage());
        }
    }

    public static class Config {
        // Configurations spécifiques au filtre
    }
}
