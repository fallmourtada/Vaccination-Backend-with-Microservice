package com.gestionclinique.appointmentservice.service;

import com.gestionclinique.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionclinique.appointmentservice.client.rest.UserServiceClient;
import com.gestionclinique.appointmentservice.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class EntityEnrichmentService {
    private final UserServiceClient userServiceClient;


    /**
     * Enrichit un utilisateur avec sa localité assignée
     *
     * @param appointment L'utilisateur à enrichir
     */
    public void enrichAppointmentWithPatient(Appointment appointment) {
        if (appointment == null || appointment.getPatientId() == null) {
            return;
        }

        try {
            UtilisateurDTO user = userServiceClient.getUserById(appointment.getPatientId());
            appointment.setPatient(user);
        } catch (feign.FeignException.NotFound e) {

            log.warn("Patient avec l'ID {} non trouvée pour le RendezVous {}",
                    appointment.getPatientId(), appointment.getId());
            // Ne définit pas la localité sur null pour ne pas perdre l'ID de référence
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du Patient avec l'ID: {}",
                    appointment.getPatientId(), e);
            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
        }
    }


    /**
     * Enrichit un utilisateur avec sa localité assignée
     *
     * @param appointment L'utilisateur à enrichir
     */
    public void enrichAppointmentWithInfirmier(Appointment appointment) {
        if (appointment == null || appointment.getInfirmierId() == null) {
            return;
        }

        try {
            UtilisateurDTO user = userServiceClient.getUserById(appointment.getInfirmierId());
            appointment.setInfirmier(user);
        } catch (feign.FeignException.NotFound e) {

            log.warn("Infirmier avec l'ID {} non trouvée pour le RendezVous {}",
                    appointment.getInfirmierId(), appointment.getId());
            // Ne définit pas la localité sur null pour ne pas perdre l'ID de référence
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'infirmier avec l'ID: {}",
                    appointment.getInfirmierId(), e);
            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
        }
    }



    public void enrichAppointmentWithAllData(Appointment  appointment) {
        if(appointment== null) {
            return;
        }


        this.enrichAppointmentWithPatient(appointment);
        this.enrichAppointmentWithInfirmier(appointment);
    }


    public void enrichAppointmentsWithAllData(Collection<Appointment> appointments) {
        if (appointments == null || appointments.isEmpty()) {
            return;
        }

        appointments.forEach(this::enrichAppointmentWithAllData);

    }


    /**
     * Enrichit une liste d'utilisateurs avec leurs localités assignées
     *
     * @param  appointments Liste d'utilisateurs à enrichir
     */
    public void enrichAppointmentsWithInfirmiers(Collection<Appointment> appointments) {
        if (appointments == null || appointments.isEmpty()) {
            return;
        }

        appointments.forEach(this::enrichAppointmentWithInfirmier);
    }


    /**
     * Enrichit une liste d'utilisateurs avec leurs localités assignées
     *
     * @param  appointments Liste d'utilisateurs à enrichir
     */
    public void enrichAppointmentsWithPatients(Collection<Appointment> appointments) {
        if (appointments == null || appointments.isEmpty()) {
            return;
        }

        appointments.forEach(this::enrichAppointmentWithPatient);
    }




}
