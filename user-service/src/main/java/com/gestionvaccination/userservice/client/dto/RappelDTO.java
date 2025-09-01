package com.gestionvaccination.userservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO représentant un rappel depuis reminder-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RappelDTO {
    
    private Long id;
    
    private Long enfantId;
    
    private Long vaccinationId;
    
    private Long vaccineId;
    
    private LocalDate dateRappelPrevue;
    
    private LocalDate dateRappelEffectuee;
    
    private String statut; // PLANIFIE, ENVOYE, EFFECTUE, REPORTE, ANNULE
    
    private String typeRappel; // SMS, EMAIL, APPEL, VISITE
    
    private String message;
    
    private String numeroTelephone;
    
    private String emailContact;
    
    private Integer tentativesEnvoi;
    
    private LocalDateTime derniereTentative;
    
    private Boolean accuseReception;
    
    private String observations;
    
    private LocalDateTime dateCreation;
    
    private LocalDateTime dateModification;
    
    // Objets enrichis depuis d'autres microservices
    private VaccinationDTO vaccination; // Données de la vaccination
    
    private VaccineDTO vaccine; // Données du vaccin
}
