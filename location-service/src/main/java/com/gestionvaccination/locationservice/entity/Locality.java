package com.gestionvaccination.locationservice.entity;

import com.gestionvaccination.locationservice.enumeration.LocalityType;
import com.gestionvaccination.locationservice.helper.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant une localité géographique (région, département, commune, quartier)
 * Utilise une structure hiérarchique auto-référentielle
 */
@Entity
@Table(name = "localities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Locality extends AbstractEntity {

    private String name;

    
    private String codification;


    @Enumerated(EnumType.STRING)
    private LocalityType type;


    @ManyToOne(cascade = CascadeType.ALL)
    private Locality parent;

    @OneToMany(mappedBy = "parent")
    private Set<Locality> children = new HashSet<>();

}
