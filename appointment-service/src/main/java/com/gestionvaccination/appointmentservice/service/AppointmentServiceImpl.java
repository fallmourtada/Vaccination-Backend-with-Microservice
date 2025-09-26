package com.gestionvaccination.appointmentservice.service;

import com.gestionvaccination.appointmentservice.client.dto.EnfantDTO;
import com.gestionvaccination.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionvaccination.appointmentservice.client.rest.UserServiceClient;
import com.gestionvaccination.appointmentservice.dto.AppointmentDTO;
import com.gestionvaccination.appointmentservice.dto.SaveAppointmentDTO;
import com.gestionvaccination.appointmentservice.dto.UpdateAppointmentDTO;
import com.gestionvaccination.appointmentservice.dto.UpdateStatutAppointmentDTO;
import com.gestionvaccination.appointmentservice.entity.Appointment;
import com.gestionvaccination.appointmentservice.exception.ResourceNotFoundException;
import com.gestionvaccination.appointmentservice.mapper.AppointmentMapper;
import com.gestionvaccination.appointmentservice.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
   private final UserServiceClient userServiceClient;
//    private final EnfantServiceClient enfantServiceClient;
    private final EntityEnrichmentService entityEnrichmentService;


    @Override
    public AppointmentDTO createAppointment(SaveAppointmentDTO saveAppointmentDTO,Long userId,Long enfantId) {

        EnfantDTO enfant =null;
        if(enfantId!=null){
            try{
                enfant = userServiceClient.getEnfantById(enfantId);
                if(enfant==null){
                    throw new ResourceNotFoundException("Enfant Non trouver Avec Id: " + enfantId);
                }


            }catch (Exception e){
                log.error("Erreur lors de la récupération de l'enfant avec ID {}: {}", enfantId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération de l'enfant: " + e.getMessage());
            }
        }

        UtilisateurDTO user =null;
        if(userId!=null){
            try{
                user  = userServiceClient.getUserById(userId);
                if(user == null){
                    throw new ResourceNotFoundException("Utilisateur Non trouver Avec Id: " + userId);
                }


            }catch (Exception e){
                log.error("Erreur lors de la récupération de l'infirmier avec ID {}: {}", userId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération de l'infirmier: " + e.getMessage());
            }
        }

        Appointment appointment = appointmentMapper.fronSaveAppointment(saveAppointmentDTO,user,enfant);

        Appointment appointment1 = appointmentRepository.save(appointment);

        //entityEnrichmentService.enrichAppointmentWithEnfant(appointment1);
        //entityEnrichmentService.enrichAppointmentWithUtilisateur(appointment1);

        return appointmentMapper.fromEntity(appointment1);
    }


    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Appointment non trouver Avec Id"+id));

        entityEnrichmentService.enrichAppointmentWithAllData(appointment);

        return appointmentMapper.fromEntity(appointment);
    }



    @Override
    public List<AppointmentDTO> getAppointmentsByEnfant(Long enfantId) {
        List<Appointment> appointments = appointmentRepository.findByEnfantId(enfantId);

        entityEnrichmentService.enrichAppointmentsWithAllData(appointments);

        List<AppointmentDTO> appointmentDTOS = appointments.stream().map
                (appointmentMapper::fromEntity)
                .collect(Collectors.toList());

        return appointmentDTOS;
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByUserId(Long userId) {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);

        entityEnrichmentService.enrichAppointmentsWithAllData(appointments);

        List<AppointmentDTO> appointmentDTOS = appointments.stream().map
                        (appointmentMapper::fromEntity)
                .collect(Collectors.toList());

        return appointmentDTOS;
    }


    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();

        entityEnrichmentService.enrichAppointmentsWithAllData(appointments);



        return appointments.stream().map
                (appointmentMapper::fromEntity).
                collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, UpdateAppointmentDTO updateAppointmentDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Appointment non trouver Avec Id"+id));

        Appointment appointment1 = appointmentMapper.partialUpdate(updateAppointmentDTO,appointment);

        Appointment appointment2 = appointmentRepository.save(appointment1);

        return appointmentMapper.fromEntity(appointment2);
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Appointment non trouver Avec Id"+id));
        appointmentRepository.delete(appointment);
        log.info("Rendez Vous Supprimer Avec Success");


    }

    @Override
    public void updateStatut(Long appointmentId, UpdateStatutAppointmentDTO updateStatutAppointmentDTO) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()->new ResourceNotFoundException("Appointment non trouver Avec Id"+appointmentId));

        appointment.setStatut(updateStatutAppointmentDTO.getStatut());
    }



    // Enrichit un rendez-vous avec les données de l'enfant et de l'utilisateur
    public Appointment enrichAppointment(Appointment appointment) {
        // Récupérer les données de l'enfant
        EnfantDTO enfant = userServiceClient.getEnfantById(appointment.getEnfantId());
        appointment.setEnfant(enfant);

        // Récupérer les données de l'utilisateur (parent)
        if (appointment.getUserId() != null) {
            UtilisateurDTO utilisateur = userServiceClient.getUserById(appointment.getUserId());
            appointment.setUtilisateur(utilisateur);
        }

        return appointment;
    }
}
