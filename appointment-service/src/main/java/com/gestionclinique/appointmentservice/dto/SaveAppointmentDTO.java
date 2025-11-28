package com.gestionclinique.appointmentservice.dto;

import com.gestionclinique.appointmentservice.enumeration.StatutRv;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
public class SaveAppointmentDTO {
//    private Long patientId;
//
//
//    private Long infirmierId;


    private StatutRv statut;


    private LocalDate date;


    private LocalTime time;


}
