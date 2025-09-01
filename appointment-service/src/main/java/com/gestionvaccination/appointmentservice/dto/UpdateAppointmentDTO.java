package com.gestionvaccination.appointmentservice.dto;


import com.gestionvaccination.appointmentservice.enumeration.StatutRv;
import lombok.Data;

@Data
public class UpdateAppointmentDTO {

    private String nomVaccinAEffectuer;

//    private boolean estEffectuer;

    private StatutRv statut;



}
