package com.gestionvaccination.auth_service1.client.dto;

import java.time.LocalDate;


import com.gestionvaccination.auth_service1.enums.UserRole;
import lombok.Data;

@Data
public class UtilisateurDTO {
        private Long id;

    private String email;

    private Long age;

    private String statutMatrimonial;


    private String telephone;

    private UserRole userRole;


    private String prenom;


    private String nom;

    private String password;

    private String adresse;

    private String matricule;

    private LocalDate dateEmbauche;

    private String profession;


    private String niveauEtude;

    private UserRole role ;


    private LocalityDTO locality;

    private CentreDTO centre;

    private String nomTuteur1;

    private String nomTuteur2;

    private String prenomTuteur1;

    private String prenomTuteur2;

    private String numeroTuteur1;

    private String numeroTuteur2;
}
