package com.gestionclinique.userservice.entites;
import com.gestionclinique.userservice.helpers.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import com.gestionclinique.userservice.enumeration.UserRole;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entité représentant un utilisateur du système de vaccination
 */
@Entity
@Table(name = "utilisateurs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur extends AbstractEntity {
    
    @Column(unique = true, nullable = false)
    private String email;

    //@Column(unique = false, nullable = false)
    private Long age;

    //private String password;

    @Column(unique = true)
    private String telephone;
    
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    private String prenom;

    private LocalDate dateEmbauche;

    private String matricule;

    private String nom;
    
    private String adresse;

    
    private String profession;



    @Transient
    private Set<UserRole> roles = new HashSet<>();


    private String keycloakId;


}
