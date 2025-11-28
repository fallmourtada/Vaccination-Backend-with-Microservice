package com.gestionclinique.appointmentservice.service;

import com.gestionclinique.appointmentservice.dto.AppointmentDTO;
import com.gestionclinique.appointmentservice.dto.SaveAppointmentDTO;
import com.gestionclinique.appointmentservice.dto.UpdateAppointmentDTO;
import com.gestionclinique.appointmentservice.dto.UpdateStatutAppointmentDTO;
import com.gestionclinique.appointmentservice.entity.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface du service de gestion des Rendez-vous (Appointments).
 * Définit toutes les opérations métier pour la création, la consultation et la mise à jour
 * des rendez-vous de vaccination.
 */
@Service // Bien que ce soit une interface, l'annotation Service est souvent placée ici pour indiquer le rôle.
public interface AppointmentService {

    /**
     * Crée un nouveau rendez-vous de vaccination.
     *
     * @param saveAppointmentDTO Les données nécessaires pour la création du rendez-vous (date, heure, vaccin prévu, etc.).
     * @param patientId L'ID de l'utilisateur (Infirmier ou système) qui crée le rendez-vous.
     * @param infirmierId L'ID de l'enfant concerné par le rendez-vous.
     * @return L'objet AppointmentDTO du rendez-vous créé.
     */
    AppointmentDTO createAppointment(SaveAppointmentDTO saveAppointmentDTO, Long patientId,Long infirmierId);


    /**
     * Récupère un rendez-vous spécifique par son identifiant unique.
     *
     * @param id L'ID du rendez-vous.
     * @return L'objet AppointmentDTO correspondant.
     */
    AppointmentDTO getAppointmentById(Long id);


//    /**
//     * Récupère la liste de tous les rendez-vous gérés ou attribués à un utilisateur (Infirmier).
//     *
//     * @param userId L'ID de l'utilisateur (Infirmier).
//     * @return Une liste des AppointmentDTO attribués à cet utilisateur.
//     */
//    List<AppointmentDTO> getAppointmentsByUserId(Long userId);

    /**
     * Récupère la liste de tous les rendez-vous enregistrés dans le système.
     * (Souvent utilisé par les administrateurs pour une vue globale).
     *
     * @return Une liste de tous les objets AppointmentDTO.
     */
    List<AppointmentDTO> getAllAppointments();

    /**
     * Met à jour les détails d'un rendez-vous existant (heure, date, vaccin prévu).
     *
     * @param id L'ID du rendez-vous à mettre à jour.
     * @param updateAppointmentDTO Les nouvelles données à appliquer.
     * @return L'objet AppointmentDTO mis à jour.
     */
    AppointmentDTO updateAppointment(Long id, UpdateAppointmentDTO updateAppointmentDTO);

    /**
     * Supprime un rendez-vous du système.
     *
     * @param id L'ID du rendez-vous à supprimer.
     */
    void deleteAppointment(Long id);

    /**
     * Met à jour le statut d'un rendez-vous (par exemple : PRIS, CONFIRMÉ, ANNULÉ, EFFECTUÉ).
     *
     * @param appointmentId L'ID du rendez-vous dont le statut doit être modifié.
     * @param updateStatutAppointmentDTO L'objet contenant le nouveau statut (StatutRv).
     */
    public void updateStatut(Long appointmentId, UpdateStatutAppointmentDTO updateStatutAppointmentDTO);

    /**
     * Enrichit l'entité Appointment avec des données provenant d'autres microservices.
     * Cette méthode est utilisée en interne pour compléter les informations (Infirmier, Enfant, etc.)
     * avant de renvoyer l'objet final.
     *
     * @param appointment L'entité Appointment de base.
     * @return L'entité Appointment enrichie.
     */
    Appointment enrichAppointment(Appointment appointment);
}