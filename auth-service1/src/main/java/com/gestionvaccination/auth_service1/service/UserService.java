package com.gestionvaccination.auth_service1.service;

import com.gestionvaccination.auth_service1.dto.AuthRequestDTO;
import com.gestionvaccination.auth_service1.dto.ChangePasswordRequest;
import com.gestionvaccination.auth_service1.dto.UserDto;
import com.gestionvaccination.auth_service1.dto.UserStatsDTO;
import com.gestionvaccination.auth_service1.entity.User;
import com.gestionvaccination.auth_service1.enums.UserRole;
import com.gestionvaccination.auth_service1.exception.UserNotFoundException;
import com.gestionvaccination.auth_service1.mapper.UserMapper;
import com.gestionvaccination.auth_service1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service pour la gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    /**
     * Change le mot de passe d'un utilisateur
     */
    public void changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("Utilisateur non trouvé : " + username);
        }

        // Vérifier le mot de passe actuel
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Mot de passe actuel incorrect");
        }

        // Vérifier que les nouveaux mots de passe correspondent
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Les mots de passe ne correspondent pas");
        }

        // Changer le mot de passe
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        log.info("Mot de passe changé avec succès pour l'utilisateur : {}", username);
    }

    /**
     * Vérifie si c'est le premier démarrage (aucun admin n'existe)
     */
    public boolean isFirstStartup() {
        return !userRepository.existsByRole(UserRole.ADMIN);
    }


    /**
     * Obtient des statistiques sur les utilisateurs
     */
    public UserStatsDTO getUserStats() {
        long totalUsers = userRepository.count();
        boolean hasAdmin = userRepository.existsByRole(UserRole.ADMIN);
        
        return UserStatsDTO.builder()
                .totalUsers(totalUsers)
                .hasAdmin(hasAdmin)
                .build();
    }



    public UserDto saveUser(AuthRequestDTO authRequestDTO){
        User user = new User();
        user.setUsername(authRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(authRequestDTO.getPassword()));
        user.setRole(authRequestDTO.getRole());
        user.setEnabled(true);
        User user1 = userRepository.save(user);
        UserDto userDto = userMapper.toDto(user1);

        return userDto;
    }
}

