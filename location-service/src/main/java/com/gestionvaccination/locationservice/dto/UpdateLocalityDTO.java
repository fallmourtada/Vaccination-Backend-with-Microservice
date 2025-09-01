package com.gestionvaccination.locationservice.dto;

import com.gestionvaccination.locationservice.enumeration.LocalityType;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO pour la mise à jour d'une localité existante
 */
@Data
public class UpdateLocalityDTO implements Serializable {
    private String name;
    private LocalityType type;
       private Long parentId;
    private String codification;

}
