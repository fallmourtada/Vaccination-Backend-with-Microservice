package com.gestionvaccination.userservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO représentant une vaccination depuis vaccination-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationDTO {
    
    private Long id;
    
    private Long enfantId;
    
    private Long vaccineId;
    
    private LocalDate dateVaccination;
    
    private String lieuVaccination;
    
    private String numeroLot;
    
    private LocalDate dateExpiration;
    
    private String professionnelSanteId;
    
    private String dosage;
    
    private String voieAdministration;
    
    private String observations;
    
    private String statut; // PLANIFIEE, EFFECTUEE, REPORTEE, ANNULEE
    
    private Boolean rappelNecessaire;
    
    private LocalDate prochainRappel;
    
    private LocalDateTime dateCreation;
    
    private LocalDateTime dateModification;
    
    // Objets enrichis depuis d'autres microservices
    private VaccineDTO vaccine; // Données du vaccin depuis vaccine-service
    
    private Object professionnelSante; // Données du professionnel depuis user-service
}
