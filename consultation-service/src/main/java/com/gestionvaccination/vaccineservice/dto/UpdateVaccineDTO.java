package com.gestionvaccination.vaccineservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gestionvaccination.vaccineservice.enumeration.VaccineType;
import com.gestionvaccination.vaccineservice.enumeration.AdministrationMode;

import java.time.LocalDate;

/**
 * DTO pour mettre à jour un vaccin existant
 */
@Data
public class UpdateVaccineDTO {
    
    private String nom;
    
    private String fabricant;
    
    private String numeroLot;
    
    private LocalDate dateProduction;
    
    @Future(message = "La date d'expiration doit être dans le futur")
    private LocalDate dateExpiration;
    
    @Size(max = 1000, message = "La description ne doit pas dépasser 1000 caractères")
    private String description;
    
    private String dosage;
    
    private VaccineType typeVaccin;
    
    private AdministrationMode modeAdministration;
    
    private String temperatureConservation;
    
    @Size(max = 1000, message = "Les contre-indications ne doivent pas dépasser 1000 caractères")
    private String contreIndications;
    
    @Size(max = 1000, message = "Les effets secondaires ne doivent pas dépasser 1000 caractères")
    private String effetsSecondaires;
    
//    @Min(value = 1, message = "Le nombre de doses requises doit être au moins 1")
//    private Integer dosesRequises;
//
//    @Min(value = 0, message = "La quantité disponible ne peut pas être négative")
//    private Integer quantiteDisponible;

    private String protection;

    private String duree;

    private String aAdministrer;

    private String precautionsAPrendre;

    private String lieuAdministration;

    private Integer dosesAdministres;

}
