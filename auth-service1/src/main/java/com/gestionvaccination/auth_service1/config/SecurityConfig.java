package com.gestionvaccination.auth_service1.config;

import com.gestionvaccination.auth_service1.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration // Cette classe configure Spring Security
@EnableWebSecurity // Active la sécurité Web de Spring
@RequiredArgsConstructor // Lombok génère un constructeur avec tous les "final"
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService; // pour charger les users depuis la BDD
    private final JwtFilter jwtFilter; // filtre qui intercepte et valide les JWT

    // Définit l'encodeur de mot de passe (ici BCrypt recommandé)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Déclare l'AuthenticationManager (coeur de l'authentification)
    // - utilise CustomUserDetailService pour charger les users
    // - vérifie les mots de passe avec BCrypt
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(customUserDetailService) // user + rôles récupérés depuis la BDD
                .passwordEncoder(passwordEncoder); // comparaison des mots de passe avec BCrypt
        return authManagerBuilder.build();
    }

    // Déclare la chaîne de filtres de sécurité
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // On désactive CSRF (pas utile avec JWT car pas basé sur les sessions/cookies)
                .csrf(AbstractHttpConfigurer::disable)

                // Définition des règles d'accès
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("/api/auth/v1/**").permitAll() // les routes /api/auth/v1/** sont publiques
                        .anyRequest().authenticated() // tout le reste nécessite un JWT valide
                )

                // On ajoute notre JwtFilter avant le filtre
                // UsernamePasswordAuthenticationFilter
                // Ce filtre lit le token, le valide et authentifie l'utilisateur
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // On construit la config finale
                .build();
    }
}
