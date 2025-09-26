package com.gestionvaccination.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig1 {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                // 1. Désactiver la Protection CSRF
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // 2. Désactiver le Basic Auth et le Form Login (inutiles en microservices)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

                // 3. La configuration .oauth2ResourceServer() a été retirée.
                // Si la dépendance OAuth2 n'est pas là, cette méthode cause une erreur.
                // En la retirant, on laisse Spring Security s'ouvrir, ce qui est l'objectif
                // puisque le filtre custom JwtAuthGatewayFilterFactory gère l'authentification.

                // 4. Configurer les autorisations: Autoriser absolument tout (la sécurité est externe)
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().permitAll()
                )
                // 5. Construire et retourner la chaîne de filtres
                .build();
    }
}
