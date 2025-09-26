package com.gestionvaccination.vaccinationservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig1 {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/v1/vaccinations/byQrCode/",
                                "/api/v1/vaccinations/byQrCode/**",
                                "/api/v1/vaccinations/by-qr-code/{qrCode}/with-vaccinations",
                                "/api/v1/vaccinations/enfant/**",
                                "/api/v1/vaccines/**",
                                "/api/v1/vaccines/{vaccineId}",
                                "/api/v1/appointments/**",
                                "/api/v1/appointments/{appointmentId}",
                                "/api/v1/users/**",
                                "/api/v1/users/{userId}",
                                "/api/v1/users/enfants/{enfantId}",
                                "/api/v1/appointments/{appointmentId}/status"
                                ).permitAll()
                        .requestMatchers("/api/v1/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_INFIRMIER", "ROLE_ICP")
                        // Ajoutez vos règles spécifiques pour ce microservice
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}