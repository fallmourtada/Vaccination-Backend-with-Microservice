//package com.gestionvaccination.auth_service.service;
//
//
//import com.gestionvaccination.auth_service.client.UserServiceClient;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserServiceClient userServiceClient;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Appelle user-service pour valider l'utilisateur
//        boolean isValid = userServiceClient.validateUser(username, null);
//        if (!isValid) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        // Retourne un utilisateur fictif pour Spring Security
//        return org.springframework.security.core.userdetails.User
//                .withUsername(username)
//                .password("") // Le mot de passe n'est pas nécessaire ici
//                .roles("USER") // Rôle par défaut
//                .build();
//    }
//}