package com.gestionclinique.userservice.dto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gestionclinique.userservice.enumeration.UserRole;

import java.time.LocalDate;
import java.util.*;

/**
 * DTO pour retourner les données d'un utilisateur
 */
@Data
public class UtilisateurDTO {

    private Long id;

    private String email;

    private Long age;

    private String telephone;

    private UserRole userRole;


    private String prenom;


    private String nom;

    //private String password;

    private String adresse;

    private String matricule;

    private LocalDate dateEmbauche;

    private String profession;

    private String keycloakId;



}