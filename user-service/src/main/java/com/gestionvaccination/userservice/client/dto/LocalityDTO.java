package com.gestionvaccination.userservice.client.dto;


import com.gestionvaccination.userservice.client.enumeration.LocalityType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
