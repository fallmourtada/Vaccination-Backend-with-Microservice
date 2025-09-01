package com.gestionvaccination.userservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gestionvaccination.userservice.enumeration.Sexe;

import java.time.LocalDate;

/**
 * DTO pour sauvegarder un nouvel enfant
 */
@Data
public class SaveEnfantDTO {

    //private String contenu_qr_code;

    //private String  qr_code;

    private String prenom;
    
    private String nom;

    private LocalDate dateNaissance;
    

    private Sexe sexe;
    
    private String lieuNaissance;
    
    private String adresse;
    
    private String allergies;

    
    private String groupeSanguin;

    private Double poids;

    private Double taille;
    

    private Long parentId;


}
