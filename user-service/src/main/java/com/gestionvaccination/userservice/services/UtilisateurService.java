package com.gestionvaccination.userservice.services;

import com.gestionvaccination.userservice.dto.*;
import com.gestionvaccination.userservice.enumeration.UserRole;

import java.util.List;

/**
 * Interface du service de gestion des utilisateurs
 */
public interface UtilisateurService {

    /**
     * Créer un nouvel utilisateur
     */
    UtilisateurDTO saveUtilisateur(SaveUtilisateurDTO saveUtilisateurDTO, UserRole userRole,Long localityId,Long  centreId);

    UtilisateurDTO saveInfirmier(SaveInfirmierDTO saveInfirmierDTO,UserRole userRole,Long centreId);

    UtilisateurDTO saveParent(SaveParentDTO saveParentDTO,UserRole userRole,Long centreId);




    /**
     * Obtenir un utilisateur par ID
     */
    UtilisateurDTO getUserById(Long userId);

    /**
     * Obtenir un utilisateur par email
     */
    UtilisateurDTO getUserByEmail(String email);

    /**
     * Obtenir un utilisateur par téléphone
     */
    UtilisateurDTO getUserByTelephone(String telephone);

    /**
     * Obtenir un utilisateur par ID Keycloak
     */
    UtilisateurDTO getUserByKeycloakId(String keycloakUserId);

    /**
     * Mettre à jour un utilisateur
     */
    UtilisateurDTO updateUser(Long userId, UpdateUtilisateurDTO updateUtilisateurDTO);

    /**
     * Supprimer un utilisateur
     */
    void deleteUser(Long userId);

    /**
     * Obtenir tous les utilisateurs avec pagination
     */
    List<UtilisateurDTO> getAllUsers();



}