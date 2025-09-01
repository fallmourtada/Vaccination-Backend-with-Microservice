package com.gestionvaccination.appointmentservice.service;

import com.gestionvaccination.appointmentservice.client.dto.EnfantDTO;
import com.gestionvaccination.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionvaccination.appointmentservice.client.rest.UserServiceClient;
import com.gestionvaccination.appointmentservice.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.descriptor.web.ApplicationParameter;
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
    public void enrichAppointmentWithUtilisatur(Appointment appointment) {
        if (appointment == null || appointment.getUserId() == null) {
            return;
        }

        try {
            UtilisateurDTO user = userServiceClient.getUserById(appointment.getUserId());
            appointment.setUtilisateur(user);
        } catch (feign.FeignException.NotFound e) {

            log.warn("Utilisateur avec l'ID {} non trouvée pour le RendezVous {}",
                    appointment.getUserId(), appointment.getId());
            // Ne définit pas la localité sur null pour ne pas perdre l'ID de référence
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'utilisateur avec l'ID: {}",
                    appointment.getUserId(), e);
            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
        }
    }


    public void enrichAppointmentWithAllData(Appointment  appointment) {
        if(appointment== null) {
            return;
        }

        this.enrichAppointmentWithEnfant(appointment);
        this.enrichAppointmentWithUtilisatur(appointment);
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
    public void enrichAppointmentsWithUtilisateurs(Collection<Appointment> appointments) {
        if (appointments == null || appointments.isEmpty()) {
            return;
        }

        appointments.forEach(this::enrichAppointmentWithUtilisatur);
    }




    public void enrichAppointmentWithEnfant(Appointment appointment) {
        if(appointment == null || appointment.getEnfantId() == null) {
            return;
        }

        try {
            EnfantDTO enfant  = userServiceClient.getEnfantById(appointment.getEnfantId());
            appointment.setEnfant(enfant);

        } catch (feign.FeignException.NotFound e) {
            log.warn("RendezVous  avec l'ID {} non trouvée pour l'enfant{}",
                    appointment.getId(), appointment.getEnfantId());

            // Ne définit pas la localité sur null pour ne pas perdre l'ID de référence
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'enfant avec l'ID: {}",
                    appointment.getEnfantId(), e);
            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
        }
    }




    public void enrichAppointmentsWithEnfants(Collection<Appointment> appointments) {
        if (appointments == null || appointments.isEmpty()) {
            return;
        }

        appointments.forEach(this::enrichAppointmentWithEnfant);
    }










}
