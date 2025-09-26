package com.gestionvaccination.userservice.services;

import com.gestionvaccination.userservice.dto.*;
import com.gestionvaccination.userservice.enumeration.UserRole;

import java.util.List;

/**
 * Interface du service de gestion des utilisateurs.
 * Définit toutes les opérations métier pour la gestion des différents types d'utilisateurs
 * (Infirmier, Parent, etc.) dans le système.
 */
public interface UtilisateurService {

    // Note : L'ancienne méthode générique saveUtilisateur a été commentée,
    // et remplacée par des méthodes spécifiques par rôle pour plus de clarté.
    //
    // UtilisateurDTO saveUtilisateur(SaveUtilisateurDTO saveUtilisateurDTO, UserRole userRole,Long localityId,Long  centreId);

    /**
     * Crée un nouvel utilisateur avec le rôle d'Infirmier.
     *
     * @param saveInfirmierDTO Les données spécifiques de l'Infirmier à sauvegarder.
     * @param userRole Le rôle de l'utilisateur (doit être INFIRMIER).
     * @param centreId L'ID du centre de santé auquel l'infirmier est affecté.
     * @return L'objet UtilisateurDTO de l'Infirmier créé.
     */
    UtilisateurDTO saveInfirmier(SaveInfirmierDTO saveInfirmierDTO, UserRole userRole, Long centreId);

    /**
     * Crée un nouvel utilisateur avec le rôle de Parent.
     *
     * @param saveParentDTO Les données spécifiques du Parent à sauvegarder.
     * @param userRole Le rôle de l'utilisateur (doit être PARENT).
     * @param centreId L'ID du centre de santé de rattachement du parent (ou null si non pertinent).
     * @return L'objet UtilisateurDTO du Parent créé.
     */
    UtilisateurDTO saveParent(SaveParentDTO saveParentDTO, UserRole userRole, Long centreId);


    //--- Méthodes de Recherche par Identifiant ---

    /**
     * Récupère un utilisateur par son identifiant unique.
     *
     * @param userId L'ID de l'utilisateur.
     * @return L'objet UtilisateurDTO correspondant.
     */
    UtilisateurDTO getUserById(Long userId);

    /**
     * Récupère un utilisateur par son adresse email.
     *
     * @param email L'adresse email de l'utilisateur (doit être unique).
     * @return L'objet UtilisateurDTO correspondant.
     */
    UtilisateurDTO getUserByEmail(String email);

    /**
     * Récupère un utilisateur par son numéro de téléphone.
     *
     * @param telephone Le numéro de téléphone de l'utilisateur.
     * @return L'objet UtilisateurDTO correspondant.
     */
    UtilisateurDTO getUserByTelephone(String telephone);

    /**
     * Récupère un utilisateur par son ID dans le système d'authentification Keycloak.
     *
     * @param keycloakUserId L'identifiant unique Keycloak de l'utilisateur.
     * @return L'objet UtilisateurDTO correspondant.
     */
    UtilisateurDTO getUserByKeycloakId(String keycloakUserId);


    //--- Méthodes de Gestion et Listes ---

    /**
     * Met à jour les informations générales d'un utilisateur existant.
     *
     * @param userId L'ID de l'utilisateur à mettre à jour.
     * @param updateUtilisateurDTO Les nouvelles données à appliquer (nom, téléphone, etc.).
     * @return L'objet UtilisateurDTO mis à jour.
     */
    UtilisateurDTO updateUser(Long userId, UpdateUtilisateurDTO updateUtilisateurDTO);

    /**
     * Supprime un utilisateur de la base de données.
     *
     * @param userId L'ID de l'utilisateur à supprimer.
     */
    void deleteUser(Long userId);

    /**
     * Récupère la liste de tous les utilisateurs enregistrés dans le système.
     *
     * @return Une liste d'objets UtilisateurDTO.
     */
    List<UtilisateurDTO> getAllUsers();

}