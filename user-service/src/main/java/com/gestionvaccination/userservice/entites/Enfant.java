package com.gestionvaccination.userservice.entites;

import com.gestionvaccination.userservice.helpers.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import com.gestionvaccination.userservice.enumeration.Sexe;

import java.time.LocalDate;

/**
 * Entité représentant un enfant dans le système de vaccination
 */
@Entity
@Table(name = "enfants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enfant extends AbstractEntity {

    private String contenu_qr_code;

    private String  qr_code;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, name = "date_naissance")
    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;


    // Informations médicales
    private String allergies;


    @Column(name = "groupe_sanguin")
    private String groupeSanguin;

    private Double poids; // en kg

    private Double taille; // en cm

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Utilisateur parent;


}