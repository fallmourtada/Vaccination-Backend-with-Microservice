package com.gestionvaccination.vaccinationservice.client.dto;


import com.gestionvaccination.vaccinationservice.client.enumeration.StatutRv;
import lombok.Data;

@Data
public class UpdateStatutAppointmentDTO {
    private StatutRv statut;
}
