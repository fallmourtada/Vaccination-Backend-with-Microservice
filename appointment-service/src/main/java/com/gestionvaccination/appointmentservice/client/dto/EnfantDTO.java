package com.gestionvaccination.appointmentservice.client.dto;


import com.gestionvaccination.appointmentservice.client.enumeration.Sexe;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * DTO pour retourner les données d'un enfant
 */
@Data
public class EnfantDTO {
    private Long id;

    private String contenu_qr_code;

    private String  qr_code;

    private String prenom;

    private String nom;

    private LocalDate dateNaissance;

    private Sexe sexe;

    private String lieuNaissance;

    private String allergies;

    private String groupeSanguin;

    private Double poids; // en kg

    private Double taille; // en cm

    private UtilisateurDTO parent;

    private Date createdAt;

    private Date updatedAt;


}



