package com.gestionclinique.appointmentservice.dto;


import com.gestionclinique.appointmentservice.enumeration.StatutRv;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateAppointmentDTO {
    private StatutRv statut;


    private LocalDate date;


    private LocalTime time;



}
