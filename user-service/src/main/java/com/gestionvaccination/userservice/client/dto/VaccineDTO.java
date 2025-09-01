package com.gestionvaccination.userservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO représentant un vaccin depuis vaccine-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineDTO {
    
    private Long id;
    
    private String nom;
    
    private String nomCommercial;
    
    private String fabricant;
    
    private String description;
    
    private String typeVaccin; // VIVANT_ATTENUE, INACTIVE, TOXOIDE, etc.
    
    private String voieAdministration; // INTRAMUSCULAIRE, ORALE, etc.
    
    private String dosage;
    
    private Integer nombreDoses;
    
    private Integer intervalleEntreDozes; // en jours
    
    private Integer ageMinimum; // en mois
    
    private Integer ageMaximum; // en mois
    
    private String contrindications;
    
    private String effetsSecondaires;
    
    private String conservation; // conditions de conservation
    
    private Boolean obligatoire; // vaccin obligatoire au Sénégal
    
    private Boolean actif;
    
    private String codeOMS; // code OMS du vaccin
}
