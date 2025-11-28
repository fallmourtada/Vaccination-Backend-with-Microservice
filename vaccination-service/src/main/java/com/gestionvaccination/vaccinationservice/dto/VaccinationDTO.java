package com.gestionvaccination.vaccinationservice.dto;

import com.gestionvaccination.vaccinationservice.client.dto.*;
import com.gestionvaccination.vaccinationservice.enumeration.StatutVaccination;
import com.gestionvaccination.vaccinationservice.enumeration.TypeVaccination;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO pour retourner les informations d'une vaccination
 */
@Data

public class VaccinationDTO {
    private Long id;

//    private boolean estEffectuer;


    private StatutVaccination statutVaccination;

    private TypeVaccination typeVaccination;


    private LocalDate date;


//    private UtilisateurDTO utilisateur;

    private UtilisateurDTO infirmier;

    private UtilisateurDTO parent;

    private AppointmentDTO appointment;

    private VaccineDTO vaccine;

    private EnfantDTO enfant;
}
