package com.gestionvaccination.userservice.dto;

import com.gestionvaccination.userservice.client.dto.CentreDTO;
import com.gestionvaccination.userservice.client.dto.LocalityDTO;
import com.gestionvaccination.userservice.entites.Enfant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gestionvaccination.userservice.enumeration.UserRole;
import com.gestionvaccination.userservice.enumeration.StatutUtilisateur;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

    private UserRole userRole;


    private String prenom;


    private String nom;

    private String adresse;

    private String matricule;

    private LocalDate dateEmbauche;

    private String profession;


    private String niveauEtude;

    private Set<UserRole> roles ;


    private LocalityDTO locality;

    private CentreDTO centre;

    private String nomTuteur1;

    private String nomTuteur2;

    private String prenomTuteur1;

    private String prenomTuteur2;

    private String numeroTuteur1;

    private String numeroTuteur2;

    private Date createdAt;

    private Date updatedAt;

    private List<EnfantDTO> enfants;


}