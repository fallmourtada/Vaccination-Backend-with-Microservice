package com.gestionclinique.userservice.config;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration pour les clients Feign
 * Cette classe configure les intercepteurs de requêtes Feign pour propager le token JWT
 * aux services en aval.
 */
@Configuration
public class FeignClientConfig {

    /**
     * Crée un intercepteur de requêtes qui ajoute l'en-tête Authorization
     * avec le token JWT de l'utilisateur actuel à toutes les requêtes sortantes.
     *
     * @return L'intercepteur de requêtes configuré
     */
//    @Bean
//    public RequestInterceptor requestTokenBearerInterceptor() {
//        return requestTemplate -> {
//            // Récupérer le token JWT de l'utilisateur authentifié
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication != null && authentication instanceof JwtAuthenticationToken) {
//                JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
//                String token = jwtAuthentication.getToken().getTokenValue();
//
//                // Ajouter le token à l'en-tête Authorization de la requête sortante
//                requestTemplate.header("Authorization", "Bearer " + token);
//            } else {
//                // Tentative alternative de récupération du token à partir de la requête HTTP entrante
//                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//                if (requestAttributes != null) {
//                    String authorizationHeader = requestAttributes.getRequest().getHeader("Authorization");
//                    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                        requestTemplate.header("Authorization", authorizationHeader);
//                    }
//                }
//            }
//        };
//    }
}
