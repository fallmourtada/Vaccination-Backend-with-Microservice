package com.gestionvaccination.vaccinationservice.entity;

import com.gestionvaccination.vaccinationservice.client.dto.*;
import com.gestionvaccination.vaccinationservice.enumeration.StatutVaccination;
import com.gestionvaccination.vaccinationservice.helpers.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Entité représentant une vaccination réalisée
 */
@Entity
@Table(name = "vaccinations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Vaccination extends AbstractEntity {

//    private boolean estEffectuer;

    @Column(nullable = false)
    private Long enfantId;

    @Column(nullable = false)
    private Long vaccineId;


    @Column(nullable = false)
    private Long appointmentId;

    @Enumerated(EnumType.STRING)
    private StatutVaccination statutVaccination;

    private Long userId;


    private LocalDate date;


    @Transient
    private UtilisateurDTO utilisateur;


    @Transient
    private AppointmentDTO appointment;


    @Transient
    private VaccineDTO vaccine;

    @Transient
    private EnfantDTO enfant;


}
