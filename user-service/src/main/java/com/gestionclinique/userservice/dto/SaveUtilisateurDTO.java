package com.gestionclinique.userservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO pour sauvegarder un nouvel utilisateur
 */
@Data
public class SaveUtilisateurDTO {

    private String password;

    private String email;

    private Long age;

    
    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^(\\+221)?[0-9]{9}$", message = "Format de téléphone sénégalais invalide")
    private String telephone;

    

    private String prenom;


    private String matricule;
    

    private String nom;
    
    private String adresse;
    

    private String profession;
    
    //private String keycloakUserId; // Fourni par Keycloak après création
}
