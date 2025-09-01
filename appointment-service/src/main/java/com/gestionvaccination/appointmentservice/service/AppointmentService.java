package com.gestionvaccination.appointmentservice.service;

import com.gestionvaccination.appointmentservice.dto.AppointmentDTO;
import com.gestionvaccination.appointmentservice.dto.SaveAppointmentDTO;
import com.gestionvaccination.appointmentservice.dto.UpdateAppointmentDTO;
import com.gestionvaccination.appointmentservice.dto.UpdateStatutAppointmentDTO;
import com.gestionvaccination.appointmentservice.enumeration.StatutRv;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {
    AppointmentDTO createAppointment(SaveAppointmentDTO saveAppointmentDTO,Long userId,Long enfantId);
    AppointmentDTO getAppointmentById(Long id);
    List<AppointmentDTO> getAppointmentsByEnfant(Long enfantId);
    List<AppointmentDTO> getAppointmentsByUserId(Long userId);
    List<AppointmentDTO> getAllAppointments();
    AppointmentDTO updateAppointment(Long id, UpdateAppointmentDTO updateAppointmentDTO);
    void deleteAppointment(Long id);

    public void updateStatut(Long appointmentId, UpdateStatutAppointmentDTO updateStatutAppointmentDTO);
}
