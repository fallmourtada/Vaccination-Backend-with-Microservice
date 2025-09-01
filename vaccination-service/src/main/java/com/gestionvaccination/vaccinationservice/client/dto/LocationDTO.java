package com.gestionvaccination.vaccinationservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour les données de la localité provenant du location-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Long id;
    private String nom;
    private String code;
    private String type;
    private LocationDTO parent;
    private Integer level;
}
