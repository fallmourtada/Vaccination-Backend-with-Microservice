package com.gestionclinique.appointmentservice.mapper;

import com.gestionclinique.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionclinique.appointmentservice.dto.AppointmentDTO;
import com.gestionclinique.appointmentservice.dto.SaveAppointmentDTO;
import com.gestionclinique.appointmentservice.dto.UpdateAppointmentDTO;
import com.gestionclinique.appointmentservice.entity.Appointment;
import com.gestionclinique.appointmentservice.enumeration.StatutRv;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class AppointmentMapper {

    public AppointmentDTO fromEntity(Appointment appointment) {
        if(appointment == null)
            return null;

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setStatut(appointment.getStatut());
        appointmentDTO.setDate(appointment.getDate());
        appointmentDTO.setTime(appointment.getTime());

        if(appointment.getPatientId() != null)
            appointmentDTO.setPatient(appointment.getPatient());

        if(appointment.getInfirmierId() != null)
            appointmentDTO.setInfirmier(appointment.getInfirmier());


        return appointmentDTO;
    }





    public Appointment fronSaveAppointment(SaveAppointmentDTO saveAppointmentDTO,
                                           UtilisateurDTO infirmier,
                                           UtilisateurDTO patient
                                           ) {
        if(saveAppointmentDTO == null)
            return null;

        Appointment appointment = new Appointment();
        appointment.setStatut(StatutRv.PROGRAMMER);
        appointment.setDate(LocalDate.now());
        appointment.setTime(LocalTime.now());


        // --- Vérification pour Infirmier (OK, vous vérifiez déjà le .getId() interne, mais l'objet doit être vérifié d'abord) ---
        // Note: Pour une meilleure robustesse, vérifiez si infirmier n'est pas null AVANT d'appeler infirmier.getId()
        if (infirmier != null && infirmier.getId() != null) {
            appointment.setInfirmierId(infirmier.getId());
            appointment.setInfirmier(infirmier);
        }else {
            appointment.setInfirmierId(null);
            appointment.setInfirmier(null);
        }


        // --- Vérification pour Parent (Idem) ---
        if (patient != null && patient.getId() != null) {
            appointment.setPatientId(patient.getId());
            appointment.setPatient(patient);
        } else {
            appointment.setPatientId(null);
            appointment.setPatient(null);
        }


        return appointment;
    }



    public Appointment partialUpdate(UpdateAppointmentDTO updateAppointmentDTO,Appointment appointment){
        if(updateAppointmentDTO == null || appointment == null)
            return null;

        appointment.setStatut(updateAppointmentDTO.getStatut());
        appointment.setDate(updateAppointmentDTO.getDate());
        appointment.setTime(updateAppointmentDTO.getTime());

        return appointment;
    }

}
