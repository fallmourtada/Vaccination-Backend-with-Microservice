package com.gestionvaccination.appointmentservice.dto;

import com.gestionvaccination.appointmentservice.client.dto.EnfantDTO;
import com.gestionvaccination.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionvaccination.appointmentservice.enumeration.StatutRv;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AppointmentDTO {
    private Long id;

    private String nomVaccinAEffectuer;


    private StatutRv statut;

//    private boolean estEffectuer;


    private LocalDate date;

    private EnfantDTO enfant;

    private UtilisateurDTO utilisateur;


}
