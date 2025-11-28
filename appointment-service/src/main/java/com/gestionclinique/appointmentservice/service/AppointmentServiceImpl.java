package com.gestionclinique.appointmentservice.service;

import com.gestionclinique.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionclinique.appointmentservice.client.rest.UserServiceClient;
import com.gestionclinique.appointmentservice.dto.AppointmentDTO;
import com.gestionclinique.appointmentservice.dto.SaveAppointmentDTO;
import com.gestionclinique.appointmentservice.dto.UpdateAppointmentDTO;
import com.gestionclinique.appointmentservice.dto.UpdateStatutAppointmentDTO;
import com.gestionclinique.appointmentservice.entity.Appointment;
import com.gestionclinique.appointmentservice.exception.ResourceNotFoundException;
import com.gestionclinique.appointmentservice.mapper.AppointmentMapper;
import com.gestionclinique.appointmentservice.repository.AppointmentRepository;
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
    private final EntityEnrichmentService entityEnrichmentService;


//    @Override
//    public AppointmentDTO createAppointment(SaveAppointmentDTO saveAppointmentDTO,Long userId,Long enfantId) {
//
//        EnfantDTO enfant =null;
//        if(enfantId!=null){
//            try{
//                enfant = userServiceClient.getEnfantById(enfantId);
//                if(enfant==null){
//                    throw new ResourceNotFoundException("Enfant Non trouver Avec Id: " + enfantId);
//                }
//
//
//            }catch (Exception e){
//                log.error("Erreur lors de la récupération de l'enfant avec ID {}: {}", enfantId, e.getMessage());
//                throw new ResourceNotFoundException("Erreur lors de la récupération de l'enfant: " + e.getMessage());
//            }
//        }
//
//        UtilisateurDTO user =null;
//        if(userId!=null){
//            try{
//                user  = userServiceClient.getUserById(userId);
//                if(user == null){
//                    throw new ResourceNotFoundException("Utilisateur Non trouver Avec Id: " + userId);
//                }
//
//
//            }catch (Exception e){
//                log.error("Erreur lors de la récupération de l'infirmier avec ID {}: {}", userId, e.getMessage());
//                throw new ResourceNotFoundException("Erreur lors de la récupération de l'infirmier: " + e.getMessage());
//            }
//        }
//
//        Appointment appointment = appointmentMapper.fronSaveAppointment(saveAppointmentDTO,user,enfant);
//
//        Appointment appointment1 = appointmentRepository.save(appointment);
//
//        //entityEnrichmentService.enrichAppointmentWithEnfant(appointment1);
//        //entityEnrichmentService.enrichAppointmentWithUtilisateur(appointment1);
//
//        return appointmentMapper.fromEntity(appointment1);
//    }



    @Override
    public AppointmentDTO createAppointment(SaveAppointmentDTO saveAppointmentDTO, Long patientId, Long infirmierId) {

        UtilisateurDTO patient =null;
        if(patientId!=null){
            try{
                patient  = userServiceClient.getUserById(patientId);
                if(patient == null){
                    throw new ResourceNotFoundException("Patient Non trouver Avec Id: " + patientId);
                }


            }catch (Exception e){
                log.error("Erreur lors de la récupération du patient  avec ID {}: {}", patientId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération du parent: " + e.getMessage());
            }
        }


        UtilisateurDTO infirmier =null;
        if(infirmierId!=null){
            try{
                infirmier  = userServiceClient.getUserById(infirmierId);
                if(infirmier == null){
                    throw new ResourceNotFoundException("Infirmier Non trouver Avec Id: " + infirmierId);
                }


            }catch (Exception e){
                log.error("Erreur lors de la récupération de l'infirmier  avec ID {}: {}", infirmierId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération de l'infirmier: " + e.getMessage());
            }
        }


        Appointment appointment = appointmentMapper.fronSaveAppointment(saveAppointmentDTO,patient,infirmier);

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

        // Récupérer les données de l'utilisateur (parent)
        if (appointment.getPatientId() != null) {
            UtilisateurDTO patient = userServiceClient.getUserById(appointment.getPatientId());
            appointment.setPatient(patient);
        }

        // Récupérer les données de l'utilisateur (parent)
        if (appointment.getInfirmierId()!= null) {
            UtilisateurDTO infirmier = userServiceClient.getUserById(appointment.getInfirmierId());
            appointment.setInfirmier(infirmier);
        }

        return appointment;
    }

}
