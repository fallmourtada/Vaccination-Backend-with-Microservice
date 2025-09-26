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
 */
@RefreshScope
@Component
public class JwtAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthGatewayFilterFactory.Config> {


    // Rendre ce champ 'final' car il sera initialisé dans le constructeur.
    private final String secretKey;

//    @Value("${app.jwt-secret}")
//    private String secretKey;
//
//    public JwtAuthGatewayFilterFactory() {
//        super(Config.class);
//    }

    // ATTENTION : Ce constructeur est MAINTENANT OBLIGATOIRE
    // Il permet à Spring de passer la valeur de la propriété lors de la création du bean.
    public JwtAuthGatewayFilterFactory(@Value("${app.jwt-secret}") String secretKey) {
        super(Config.class);
        this.secretKey = secretKey; // Assignation du secret à la variable de classe
    }

    // Liste des préfixes de chemins publics qui NE DOIVENT PAS être sécurisés par JWT
    public static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/api/auth/v1",                     // Route vers le service d'authentification
            "/api/v1/users",                   // Make base user endpoint public
            "/api/v1/users/",                  // Including trailing slash
            "/api/v1/users/validate",          // User validation endpoint
            "/api/v1/users/register",          // Registration endpoint
            "/api/v1/users/login",// Exemple: Ajout d'une nouvelle route
            " /v3/api-docs/**",                 // Swagger docs\n" +
            "/swagger-ui/**",                  // Swagger UI\n" +
            "/swagger-ui.html ",
            "/api/v1/localities/centres/{centreId}",
            "/api/v1/localities/{localityId}",
            "/api/v1/vaccines/{vaccinId}",
            "/api/v1/vaccinations/byQrCode/",
            "/api/v1/vaccinations/byQrCode/**",
            "/api/v1/vaccinations/by-qr-code/{qrCode}/with-vaccinations",
            "/api/v1/vaccinations/enfant/**",
            "/api/v1/vaccines/**",
            "/api/v1/vaccines/{vaccineId}",
            "/api/v1/appointments/**",
            "/api/v1/appointments/{appointmentId}",
            "/api/v1/users/{userId}",
            "/api/v1/users/enfants/{enfantId}",
            "/api/v1/appointments/{appointmentId}/status",
            "/api/v1/users/{userId}",
            "/api/auth/v1/users",
            "/api/v1/users/enfants/{enfantId}",
            "/api/v1/users/enfants/by-qr-code/{qrCode}",
            "/api/v1/users/findUserByEmail/{email}",
            "/api/v1/users/stats/by-centre/{centreId}/enfants","/api/v1/appointments/test-reminders",
            "/api/v1/appointments/{appointmentId}",
            "/api/v1/appointments/{appointmentId}/status"
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
            throw new RuntimeException("Problème critique avec la clé JWT dans la Gateway : " + e.getMessage());
        }
    }

    public static class Config {
        // Configurations spécifiques au filtre
    }
}