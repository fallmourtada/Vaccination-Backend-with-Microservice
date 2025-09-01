package com.gestionvaccination.locationservice.dto;

import com.gestionvaccination.locationservice.enumeration.LocalityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO pour la création d'une nouvelle localité
 */
@Data
public class SaveLocalityDTO implements Serializable {
    
    @NotBlank(message = "Le nom de la localité est obligatoire")
    private String name;
    
    private String codification;
    
    @NotNull(message = "Le type de localité est obligatoire")
    private LocalityType type;
    
    private Long parentId; // ID du parent (null pour les régions)
    

}
