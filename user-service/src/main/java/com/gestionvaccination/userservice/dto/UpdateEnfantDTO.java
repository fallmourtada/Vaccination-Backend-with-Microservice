package com.gestionvaccination.userservice.dto;

import com.gestionvaccination.userservice.enumeration.Sexe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour mettre à jour un enfant
 */
@Data
public class UpdateEnfantDTO {
    
    private String prenom;
    
    private String nom;
    
    private String adresse;
    
    private String groupeSanguin;
    
    private Double poids;
    
    private Double taille;
    
    private Sexe sexe;

    private String allergies;
}
