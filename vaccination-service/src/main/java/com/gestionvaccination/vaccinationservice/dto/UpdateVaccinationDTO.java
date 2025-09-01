package com.gestionvaccination.vaccinationservice.dto;

import com.gestionvaccination.vaccinationservice.enumeration.StatutVaccination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO pour mettre à jour une vaccination existante
 */
@Data
@AllArgsConstructor
public class UpdateVaccinationDTO {

    private StatutVaccination statutVaccination;

    private LocalDate date;

}
