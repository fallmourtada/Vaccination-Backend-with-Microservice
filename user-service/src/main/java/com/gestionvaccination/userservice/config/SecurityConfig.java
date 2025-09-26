//package com.gestionvaccination.userservice.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import javax.crypto.spec.SecretKeySpec;
//import java.util.List;
//
//@Configuration
//public class SecurityConfig {
//
//    private final String secretKey = "6a193c0c9f7c870c73fe9e8aa3f70a6029cb10685fe2cdb1385c7fe3a86a491e";
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/v1/users/validate").permitAll()
//                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**",
//                                "/swagger-resources/**", "/webjars/**", "/swagger-ui.html","/api/v1/users/**","/api/v1/users/{userId}",
//                                "/api/v1/users/enfants/{enfantId}",
//                                "/api/v1/users/enfants/by-qr-code/{qrCode}",
//                                "/api/v1/users/findUserByEmail/{email}",
//                                "/api/v1/users/stats/by-centre/{centreId}/enfants",
//                                "/api/auth/v1/users").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()))
//                .build();
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        // Important: utiliser le même algorithme que dans auth-service
//        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA512");
//        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.setExposedHeaders(List.of("x-auth-token"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }
//}