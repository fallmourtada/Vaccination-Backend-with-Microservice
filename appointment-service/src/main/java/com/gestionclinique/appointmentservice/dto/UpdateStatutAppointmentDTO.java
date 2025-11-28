package com.gestionclinique.appointmentservice.dto;

import com.gestionclinique.appointmentservice.enumeration.StatutRv;
import lombok.Data;

@Data
public class UpdateStatutAppointmentDTO {
    private StatutRv statut;
}
