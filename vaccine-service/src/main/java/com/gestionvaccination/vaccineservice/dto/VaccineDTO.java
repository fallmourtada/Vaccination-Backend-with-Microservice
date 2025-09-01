package com.gestionvaccination.vaccineservice.dto;

import com.gestionvaccination.vaccineservice.enumeration.PeriodePrise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gestionvaccination.vaccineservice.enumeration.VaccineType;
import com.gestionvaccination.vaccineservice.enumeration.AdministrationMode;
import com.gestionvaccination.vaccineservice.client.dto.LocationDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    private String effetsSecondaires;

    
    private Integer dosesRequises;
    
    private Integer quantiteDisponible;

    private PeriodePrise periode;
    private Date createdAt;

    private Date updatedAt;

}
