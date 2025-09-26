package com.gestionvaccination.appointmentservice.service.impl;

import com.gestionvaccination.appointmentservice.client.dto.EnfantDTO;
import com.gestionvaccination.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionvaccination.appointmentservice.entity.Appointment;
import com.gestionvaccination.appointmentservice.enumeration.StatutRv;
import com.gestionvaccination.appointmentservice.repository.AppointmentRepository;
import com.gestionvaccination.appointmentservice.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class VaccinationReminderScheduler {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;
    private final EmailService emailService;

    /**
     * Planificateur qui s'exécute tous les jours à 13h40
     * Cette méthode envoie les rappels pour les rendez-vous à venir
     */
    @Scheduled(cron = "0 45 01 * * ?")
    public void sendVaccinationReminders() {
        log.info("Démarrage de l'envoi des rappels à {}", LocalDateTime.now());

        // Envoyer des rappels pour aujourd'hui
        sendRemindersForDate(LocalDate.now(), 0);

        // Envoyer des rappels pour demain
        sendRemindersForDate(LocalDate.now().plusDays(1), 1);

        // Envoyer des rappels pour après-demain
        sendRemindersForDate(LocalDate.now().plusDays(2), 2);

        log.info("Fin de l'envoi des rappels à {}", LocalDateTime.now());
    }

    private void sendRemindersForDate(LocalDate date, int daysRemaining) {
        log.info("Envoi des rappels pour les vaccinations du {}", date);

        List<Appointment> appointments = appointmentRepository.findByDateAndStatut(date, StatutRv.EN_ATTENTE);

        log.info("Trouvé {} rendez-vous pour le {}", appointments.size(), date);

        for (Appointment appointment : appointments) {
            try {
                log.info("Traitement du rendez-vous ID: {}, enfantId: {}, userId: {}",
                        appointment.getId(), appointment.getEnfantId(), appointment.getUserId());

                // Récupérer les détails de l'enfant et du parent
                Appointment enrichedAppointment = appointmentService.enrichAppointment(appointment);

                EnfantDTO enfant = enrichedAppointment.getEnfant();
                UtilisateurDTO parent = enrichedAppointment.getUtilisateur();

                if (enfant != null && parent != null && parent.getEmail() != null) {
                    log.info("Envoi du rappel à {} pour l'enfant {} {}",
                            parent.getEmail(), enfant.getPrenom(), enfant.getNom());

                    emailService.sendVaccinationReminder(
                            enfant.getParent().getEmail(),
                            parent.getNom(),
                            enfant.getPrenom(),
                            enfant.getNom(),
                            appointment.getDate(),
                            appointment.getNomVaccinAEffectuer(),
                            daysRemaining
                    );

                    log.info("Rappel envoyé avec succès");
                } else {
                    log.warn("Données manquantes pour le rendez-vous ID: {}", appointment.getId());
                    if (parent == null) log.warn("Parent manquant");
                    else if (parent.getEmail() == null) log.warn("Email du parent manquant");
                    if (enfant == null) log.warn("Enfant manquant");
                }
            } catch (Exception e) {
                log.error("Erreur lors de l'envoi du rappel pour le rendez-vous ID: {}", appointment.getId(), e);
                e.printStackTrace(); // Pour avoir la trace complète de l'erreur
            }
        }
    }

    /**
     * Méthode pour tests uniquement - s'exécute toutes les minutes
     * Décommentez cette méthode pour tester la fonctionnalité
     */
    /*
    @Scheduled(cron = "0 * * * * ?")
    public void testVaccinationReminders() {
        log.info("TEST - Démarrage de l'envoi des rappels à {}", LocalDateTime.now());
        sendVaccinationReminders();
        log.info("TEST - Fin de l'envoi des rappels");
    }
    */
}