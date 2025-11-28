package com.gestionclinique.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour mettre à jour un utilisateur
 */
@Data
public class UpdateUtilisateurDTO {

    private String email;

    @NotBlank(message = "L'age est obligatoire")
    private Long age;


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

    private String profession;


    //private String keycloakUserId; // Fourni par Keycloak après création
}
