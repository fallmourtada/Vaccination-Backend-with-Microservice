package com.gestionvaccination.auth_service1.repository;

import com.gestionvaccination.auth_service1.entity.User;
import com.gestionvaccination.auth_service1.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Trouve un utilisateur par son nom d'utilisateur
     */
    User findByUsername(String username);
    
    /**
     * Vérifie si un utilisateur avec le rôle spécifié existe
     */
    boolean existsByRole(UserRole role);
    
    /**
     * Trouve le premier utilisateur avec le rôle spécifié
     */
    User findFirstByRole(UserRole role);
}
