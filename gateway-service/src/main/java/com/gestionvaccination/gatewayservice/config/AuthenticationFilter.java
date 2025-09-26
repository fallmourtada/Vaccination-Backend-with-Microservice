//package com.gestionvaccination.gatewayservice.config;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Component
//public class AuthenticationFilter implements GlobalFilter {
//
//    private final List<String> publicEndpoints = List.of(
//            "/api/auth/v1/login",
//            "/api/auth/v1/register",
//            "/api/auth/v1/health",
//            "/v3/api-docs",
//            "/swagger-ui"
//    );
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//
//        // Vérifier si l'endpoint est public
//        if (isPublicEndpoint(request.getPath().toString())) {
//            return chain.filter(exchange);
//        }
//
//        // Vérifier si le token est présent
//        if (!request.getHeaders().containsKey("Authorization")) {
//            return onError(exchange, "Aucun jeton d'authentification fourni");
//        }
//
//        String authHeader = request.getHeaders().getFirst("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return onError(exchange, "Format de token invalide");
//        }
//
//        // Nous ne validons pas le token ici, chaque microservice le fera
//        return chain.filter(exchange);
//    }
//
//    private boolean isPublicEndpoint(String path) {
//        return publicEndpoints.stream().anyMatch(path::startsWith);
//    }
//
//    private Mono<Void> onError(ServerWebExchange exchange, String message) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        return response.setComplete();
//    }
//}