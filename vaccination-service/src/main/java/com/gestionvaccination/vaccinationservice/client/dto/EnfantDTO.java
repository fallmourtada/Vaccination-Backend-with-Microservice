package com.gestionvaccination.vaccinationservice.client.dto;

import com.gestionvaccination.vaccinationservice.client.enumeration.Sexe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO pour retourner les données d'un enfant
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnfantDTO {
    
    private Long id;
    
    private String prenom;
    
    private String nom;
    
    private LocalDate dateNaissance;
    
    private Sexe sexe;
    
    private String lieuNaissance;
    
    private String adresse;

    private String codeQr;
    
    private String allergies;
    
    private String contreIndications;
    
    private String groupeSanguin;
    
    private Double poids;
    
    private Double taille;
    
    private String numeroActeNaissance;
    
    private String lieuEnregistrement;
    
    private String contactUrgenceNom;
    
    private String contactUrgenceTelephone;
    
    private LocalDateTime dateCreation;
    
    private LocalDateTime dateModification;
    
    private UtilisateurDTO parent;
    
    private Integer ageEnMois; // Calculé automatiquement
    
    private Integer ageEnJours; // Calculé automatiquement
    

}
