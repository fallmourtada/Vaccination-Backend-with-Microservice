package com.gestionvaccination.auth_service.service;


import com.gestionvaccination.auth_service.dto.AuthRequestDTO;
import com.gestionvaccination.auth_service.enumeration.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;
    private JdbcUserDetailsManager jdbcUserDetailsManager;


    @Override
    public void  saveUser(AuthRequestDTO authRequestDTO) {

        // 1. Création dans Spring Security JDBC
        if (!jdbcUserDetailsManager.userExists(authRequestDTO.getUsername())) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(authRequestDTO.getUsername())
                    .password(passwordEncoder.encode(authRequestDTO.getPassword()))
                    .roles(authRequestDTO.getRole())
                    .build();
            jdbcUserDetailsManager.createUser(userDetails);
        } else {
            throw new RuntimeException("L'utilisateur existe déjà dans la base d'authentification.");
        }

    }
}
