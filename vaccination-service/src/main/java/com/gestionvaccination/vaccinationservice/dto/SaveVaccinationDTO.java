package com.gestionvaccination.vaccinationservice.dto;

import com.gestionvaccination.vaccinationservice.enumeration.StatutVaccination;
import com.gestionvaccination.vaccinationservice.enumeration.TypeVaccination;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO pour sauvegarder une nouvelle vaccination
 */
@Data
public class SaveVaccinationDTO {

//    @NotNull(message = "L'ID du vaccin est obligatoire")
//    private Long vaccineId;
//
//
//    @NotNull(message = "L'ID du RendezVous est obligatoire")
//    private Long appointmentId;
//
//
//    @NotNull(message = "L'ID de la L'Infirmer est obligatoire")
//    private Long userId;

    private StatutVaccination statut;

    private TypeVaccination typeVaccination;

    private Long enfantId;

    //private LocalDate date;



}
