package com.gestionvaccination.vaccinationservice.client.dto;

import com.gestionvaccination.vaccinationservice.client.enumeration.AdministrationMode;
import com.gestionvaccination.vaccinationservice.client.enumeration.VaccineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO pour retourner les informations d'un vaccin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineDTO {
    
    private Long id;
    
    private String nom;
    
    private String fabricant;
    
    private String numeroLot;
    
    private LocalDate dateProduction;
    
    private LocalDate dateExpiration;
    
    private String description;
    
    private String dosage;
    
    private VaccineType typeVaccin;
    
    private AdministrationMode modeAdministration;
    
    private String temperatureConservation;
    
    private String contreIndications;
    
    private String effetsSecondaires;
    
    private Integer ageMinimumMois;
    
    private Integer ageMaximumMois;
    
    private Integer dosesRequises;
    
    private Integer intervalleEntresDosesJours;
    
    private Boolean rappelRecommande;
    
    private Integer intervalleRappelMois;
    
    private Integer quantiteDisponible;
    
    // Données enrichies
//    private LocationDTO locality;
    
    private LocalDateTime dateCreation;
    
    private LocalDateTime dateModification;
}
