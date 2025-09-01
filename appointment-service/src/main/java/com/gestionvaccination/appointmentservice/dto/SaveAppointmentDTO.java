package com.gestionvaccination.appointmentservice.dto;

import com.gestionvaccination.appointmentservice.enumeration.StatutRv;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
public class SaveAppointmentDTO {
//    @NotNull(message = "L'ID de l'enfant est obligatoire")
//    private Long enfantId;


//    @NotNull(message = "L'ID du medcin est obligatoire")
//    private Long userId;


    private StatutRv statutRv;


    @NotNull(message = "Le nom du vaccin est obligatoire")
    private String nomVaccinAEffectuer;

//    private boolean estEffectuer;


    private LocalDate date;


}
