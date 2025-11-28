package com.gestionclinique.userservice.services;

import com.gestionclinique.userservice.dto.SaveUtilisateurDTO;
import com.gestionclinique.userservice.dto.UpdateUtilisateurDTO;
import com.gestionclinique.userservice.dto.UtilisateurDTO;
import com.gestionclinique.userservice.enumeration.UserRole;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.List;

/**
 * Interface du service de gestion des utilisateurs.
 * Définit toutes les opérations métier pour la gestion des différents types d'utilisateurs
 * (Infirmier, Parent, etc.) dans le système.
 */
public interface UtilisateurService {


    UtilisateurDTO saveUser(SaveUtilisateurDTO saveUtilisateurDTO,UserRole userRole);

    UtilisateurDTO saveInfirmier(SaveUtilisateurDTO saveUtilisateurDTO,UserRole userRole);

    UtilisateurDTO saveAdmin(SaveUtilisateurDTO saveUtilisateurDTO,UserRole userRole);

    UtilisateurDTO savePatient(SaveUtilisateurDTO saveUtilisateurDTO,UserRole userRole);


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


    UtilisateurDTO addUserRole(Long userId, UserRole role);

    UtilisateurDTO removeUserRole(Long userId, UserRole role);


}