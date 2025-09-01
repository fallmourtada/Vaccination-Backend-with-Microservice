package com.gestionvaccination.vaccinationservice.client.dto;

import com.gestionvaccination.vaccinationservice.client.enumeration.RoleUtilisateur;
import com.gestionvaccination.vaccinationservice.client.enumeration.StatutUtilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO pour retourner les données d'un utilisateur
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDTO {
    
    private Long id;
    
    private String email;
    
    private String telephone;
    
    private RoleUtilisateur roleUtilisateur;
    
    private StatutUtilisateur statut;
    
    private String prenom;
    
    private String nom;
    
    private String adresse;
    
    // Données de localisation enrichies (depuis location-service)
    private LocationDTO location;

    private String profession;
    
    private String numeroPieceIdentite;
    
    private String typePieceIdentite;
    
    private LocalDateTime dateCreation;
    
    private LocalDateTime dateModification;
    
//    private List<EnfantDTO> enfants;
//
//    // Données enrichies
//    private Object statistiquesUtilisateur;

}
