package com.gestionvaccination.locationservice.dto;

import com.gestionvaccination.locationservice.enumeration.LocalityType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO pour l'affichage des données de localité
 */
@Data
public class LocalityDTO implements Serializable {
    private Long id;

    private String name;

    private String codification;


    @Enumerated(EnumType.STRING)
    private LocalityType type;

    Date createdAt;

    Date updatedAt;


    private LocalityDTO  parent;


}
