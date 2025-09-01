package com.gestionvaccination.userservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO représentant une carte de vaccination complète depuis vaccination-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarteVaccinationDTO {
    
    private Long id;
    
    private Long enfantId;
    
    private String numeroCarteVaccination;
    
    private LocalDate dateCreation;
    
    private LocalDate derniereModification;
    
    private String statutGlobal; // A_JOUR, EN_RETARD, INCOMPLETE
    
    private Double tauxCompletion; // Pourcentage de vaccins reçus sur ceux requis
    
    private Integer nombreVaccinationsEffectuees;
    
    private Integer nombreVaccinationsRequises;
    
    private Integer nombreRappelsPendants;
    
    private LocalDate prochainRappel;
    
    private Boolean carteComplete;
    
    private String observationsGenerales;
    
    private LocalDateTime dateGeneration;
    
    // Liste des vaccinations effectuées
    private List<VaccinationDTO> vaccinations;
    
    // Liste des rappels pendants
    private List<RappelDTO> rappelsPendants;
    
    // Liste des prochains rappels
    private List<RappelDTO> prochainsRappels;
}
