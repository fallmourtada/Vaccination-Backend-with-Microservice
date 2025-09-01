package com.gestionvaccination.vaccinationservice.client.dto;

import com.gestionvaccination.vaccinationservice.client.enumeration.StatutRv;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDTO {
    private Long id;

    private String nomVaccinAEffectuer;

    //private CentreDTO centre;

    private StatutRv statut;

    private boolean estEffectuer;


    private LocalDate date;

    private LocalTime time;

    private EnfantDTO enfant;

    private UtilisateurDTO utilisateur;


}
