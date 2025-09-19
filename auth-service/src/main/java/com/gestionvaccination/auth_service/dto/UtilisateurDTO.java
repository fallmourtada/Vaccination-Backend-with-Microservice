package com.gestionvaccination.auth_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * DTO pour retourner les données d'un utilisateur
 */
@Data
public class UtilisateurDTO {

    private Long id;

    private String email;

    private Long age;

    private String statutMatrimonial;


    private String telephone;

//    private UserRole userRole;


    private String prenom;


    private String nom;

    private String password;

    private String adresse;

    private String matricule;

    private LocalDate dateEmbauche;

    private String profession;


    private String niveauEtude;

//    private Set<UserRole> roles ;
//
//
//    private LocalityDTO locality;
//
//    private CentreDTO centre;

    private String nomTuteur1;

    private String nomTuteur2;

    private String prenomTuteur1;

    private String prenomTuteur2;

    private String numeroTuteur1;

    private String numeroTuteur2;

//    private Date createdAt;
//
//    private Date updatedAt;
//
//    private List<EnfantDTO> enfants;


}