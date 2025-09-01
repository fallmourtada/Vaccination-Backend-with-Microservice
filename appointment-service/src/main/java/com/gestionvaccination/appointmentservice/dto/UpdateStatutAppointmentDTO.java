package com.gestionvaccination.appointmentservice.dto;

import com.gestionvaccination.appointmentservice.enumeration.StatutRv;
import lombok.Data;

@Data
public class UpdateStatutAppointmentDTO {
    private StatutRv statut;
}
