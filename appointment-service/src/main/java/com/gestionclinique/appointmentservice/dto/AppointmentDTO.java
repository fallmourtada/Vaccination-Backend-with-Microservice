package com.gestionclinique.appointmentservice.dto;

import com.gestionclinique.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionclinique.appointmentservice.enumeration.StatutRv;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDTO {

    private Long id;


    private StatutRv statut;


    private LocalDate date;


    private LocalTime time;


    private UtilisateurDTO infirmier;


    private UtilisateurDTO patient;



}
