package com.gestionvaccination.vaccineservice.dto;

import com.gestionvaccination.vaccineservice.enumeration.PeriodePrise;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gestionvaccination.vaccineservice.enumeration.VaccineType;
import com.gestionvaccination.vaccineservice.enumeration.AdministrationMode;

import java.time.LocalDate;

/**
 * DTO pour sauvegarder un nouveau vaccin
 */
@Data
public class SaveVaccineDTO {
    
    @NotBlank(message = "Le nom du vaccin est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;
    
    private String fabricant;
    
    @NotBlank(message = "Le numéro de lot est obligatoire")
    private String numeroLot;
    
    private LocalDate dateProduction;
    
    @NotNull(message = "La date d'expiration est obligatoire")
    @Future(message = "La date d'expiration doit être dans le futur")
    private LocalDate dateExpiration;
    
    @Size(max = 1000, message = "La description ne doit pas dépasser 1000 caractères")
    private String description;
    
    private String dosage;
    
    @NotNull(message = "Le type de vaccin est obligatoire")
    private VaccineType typeVaccin;
    
    private AdministrationMode modeAdministration;
    
    private String temperatureConservation;

    
    @Size(max = 1000, message = "Les effets secondaires ne doivent pas dépasser 1000 caractères")
    private String effetsSecondaires;

    
    @Min(value = 1, message = "Le nombre de doses requises doit être au moins 1")
    private Integer dosesRequises;
    

    @Min(value = 0, message = "La quantité disponible ne peut pas être négative")
    private Integer quantiteDisponible;

    private PeriodePrise periode;
}
