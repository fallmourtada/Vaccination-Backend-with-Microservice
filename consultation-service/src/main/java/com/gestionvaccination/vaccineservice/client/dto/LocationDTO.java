package com.gestionvaccination.vaccineservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour les données de localité venant du location-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    
    private Long id;
    
    private String nom;
    
    private String code;
    
    private String type; // REGION, DEPARTEMENT, COMMUNE
    
    private LocationDTO parent; // Parent (département pour commune, région pour département)
    
    private Integer level; // Niveau hiérarchique (1: Région, 2: Département, 3: Commune)
}
