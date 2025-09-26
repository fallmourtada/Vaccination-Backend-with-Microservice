package com.gestionvaccination.auth_service1.service;

import java.util.Collections;

import com.gestionvaccination.auth_service1.entity.User;
import com.gestionvaccination.auth_service1.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    // Supprimer cette ligne qui crée la dépendance circulaire
    // private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    // Constructeur simple avec seulement userRepository
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur introuvable: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getAuthority()))
        );
    }
}