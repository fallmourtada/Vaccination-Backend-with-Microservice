package com.gestionvaccination.appointmentservice.mapper;

import com.gestionvaccination.appointmentservice.client.dto.EnfantDTO;
import com.gestionvaccination.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionvaccination.appointmentservice.dto.AppointmentDTO;
import com.gestionvaccination.appointmentservice.dto.SaveAppointmentDTO;
import com.gestionvaccination.appointmentservice.dto.UpdateAppointmentDTO;
import com.gestionvaccination.appointmentservice.entity.Appointment;
import com.gestionvaccination.appointmentservice.enumeration.StatutRv;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AppointmentMapper {

    public AppointmentDTO fromEntity(Appointment appointment) {
        if(appointment == null)
            return null;

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setNomVaccinAEffectuer(appointment.getNomVaccinAEffectuer());
        appointmentDTO.setStatut(appointment.getStatut());
        if(appointment.getUserId()!=null)
            appointmentDTO.setUtilisateur(appointment.getUtilisateur());

        if(appointment.getEnfantId()!=null)
            appointmentDTO.setEnfant(appointment.getEnfant());

        if(appointment.getDate()!=null){
            appointmentDTO.setDate(appointment.getDate());
        }


        return appointmentDTO;
    }


    public Appointment fronSaveAppointment(SaveAppointmentDTO saveAppointmentDTO,
                                           UtilisateurDTO utilisateur,
                                           EnfantDTO enfant) {
        if(saveAppointmentDTO == null)
            return null;
        Appointment appointment = new Appointment();
        appointment.setNomVaccinAEffectuer(saveAppointmentDTO.getNomVaccinAEffectuer());
        appointment.setStatut(StatutRv.EN_ATTENTE);
        appointment.setDate(LocalDate.now());


        if(utilisateur.getId()!=null){
            appointment.setUserId(utilisateur.getId());
            appointment.setUtilisateur(utilisateur);

        }else {
            appointment.setUserId(null);
            appointment.setUtilisateur(null);
        }


        if(enfant.getId()!=null){
            appointment.setEnfantId(enfant.getId());
            appointment.setEnfant(enfant);
        }else {
            appointment.setEnfantId(null);
            appointment.setEnfant(null);
        }
        return appointment;
    }


    public Appointment partialUpdate(UpdateAppointmentDTO updateAppointmentDTO,Appointment appointment){
        if(updateAppointmentDTO == null || appointment == null)
            return null;

        appointment.setNomVaccinAEffectuer(updateAppointmentDTO.getNomVaccinAEffectuer());
        appointment.setStatut(updateAppointmentDTO.getStatut());

        return appointment;
    }

}
