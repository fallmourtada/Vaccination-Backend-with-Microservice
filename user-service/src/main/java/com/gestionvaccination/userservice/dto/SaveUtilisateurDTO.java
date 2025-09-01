package com.gestionvaccination.userservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.gestionvaccination.userservice.enumeration.UserRole;

/**
 * DTO pour sauvegarder un nouvel utilisateur
 */
@Data
public class SaveUtilisateurDTO {

    private String email;

    @NotBlank(message = "L'age est obligatoire")
    private Long age;

    @NotBlank(message = "Le statut est obligatoire")
    private String statutMatrimonial;
    
    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^(\\+221)?[0-9]{9}$", message = "Format de téléphone sénégalais invalide")
    private String telephone;
    
//    @NotNull(message = "Le rôle utilisateur est obligatoire")
//    private UserRole userRole;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String prenom;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String nom;
    
    private String adresse;
    
    // ID de la localité (stocké en base) - généralement la commune
    private Long localityId;

    private Long centreId;
    
    private String profession;

    private String nomTuteur1;

    private String nomTuteur2;

    private String prenomTuteur1;

    private String prenomTuteur2;

    private String numeroTuteur1;

    private String numeroTuteur2;


    private String niveauEtude;
    
    //private String keycloakUserId; // Fourni par Keycloak après création
}
