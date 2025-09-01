package com.gestionvaccination.userservice.client.dto;

import com.gestionvaccination.userservice.client.enumeration.LocalityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO pour recevoir les données de localisation du Location Service
 * Correspond exactement au LocalityDTO du location-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Long id;
    private String name;
    private String codification;
    private LocalityType type; // REGION, DEPARTMENT, DISTRICT, COMMUNE, NEIGHBORHOOD
    private Date createdAt;
    private Date updatedAt;
    private LocationDTO parent;
    private Long population;
    private Double superficieKm2;
    private String chefLieu;
    private Double latitude;
    private Double longitude;
    private Boolean active;
    private String description;
}
