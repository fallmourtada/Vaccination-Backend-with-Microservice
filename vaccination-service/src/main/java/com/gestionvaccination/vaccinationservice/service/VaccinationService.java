package com.gestionvaccination.vaccinationservice.service;

import com.gestionvaccination.vaccinationservice.client.enumeration.Sexe;
import com.gestionvaccination.vaccinationservice.dto.VaccinationDTO;
import com.gestionvaccination.vaccinationservice.dto.SaveVaccinationDTO;
import com.gestionvaccination.vaccinationservice.dto.UpdateVaccinationDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface du service de gestion des vaccinations.
 * Définit toutes les opérations métier pour l'enregistrement, la consultation et la gestion
 * des actes de vaccination effectués sur les enfants.
 */
public interface VaccinationService {

    /**
     * Enregistre un nouvel acte de vaccination dans le carnet de l'enfant.
     *
     * @param saveVaccinationDTO Les données spécifiques de l'acte de vaccination (date, observations, etc.).
     * @param vaccinId L'ID du vaccin utilisé (référence au microservice vaccin).
     * @param appointmentId L'ID du rendez-vous associé à cette vaccination (peut être null).
     * @param userId L'ID de l'utilisateur (Infirmier) ayant effectué l'acte.
     * @param enfantId L'ID de l'enfant qui a reçu le vaccin.
     * @return L'objet VaccinationDTO de l'acte enregistré.
     */
    VaccinationDTO saveVaccination(SaveVaccinationDTO saveVaccinationDTO, Long vaccinId, Long appointmentId, Long userId, Long enfantId);

    /**
     * Récupère un acte de vaccination spécifique par son identifiant unique.
     *
     * @param vaccinationId L'ID de l'acte de vaccination.
     * @return L'objet VaccinationDTO correspondant.
     */
    VaccinationDTO getVaccinationById(Long vaccinationId);

    /**
     * Met à jour les détails d'un acte de vaccination existant.
     *
     * @param vaccinationId L'ID de l'acte à mettre à jour.
     * @param updateVaccinationDTO Les nouvelles données à appliquer.
     * @return L'objet VaccinationDTO mis à jour.
     */
    VaccinationDTO updateVaccination(Long vaccinationId, UpdateVaccinationDTO updateVaccinationDTO);

    /**
     * Récupère la liste de tous les actes de vaccination enregistrés.
     *
     * @return Une liste de tous les objets VaccinationDTO.
     */
    List<VaccinationDTO> getAllVaccinations();

    /**
     * Supprime un acte de vaccination de la base de données.
     *
     * @param vaccinationId L'ID de l'acte à supprimer.
     */
    void deleteVaccination(Long vaccinationId);

    /**
     * Récupère tous les actes de vaccination effectués sur un enfant donné.
     * (Ceci constitue le carnet de vaccination de l'enfant).
     *
     * @param enfantId L'ID de l'enfant.
     * @return Une liste des VaccinationDTO associées à l'enfant.
     */
    List<VaccinationDTO> getVaccinationByEnfant(Long enfantId);

    /**
     * Récupère tous les actes de vaccination utilisant un vaccin spécifique.
     *
     * @param vaccineId L'ID du vaccin (ex: BCG, Polio, etc.).
     * @return Une liste des VaccinationDTO utilisant ce vaccin.
     */
    List<VaccinationDTO> getVaccinationByVaccin(Long vaccineId);

    /**
     * Récupère tous les actes de vaccination effectués dans un centre de santé spécifique.
     *
     * @param centreId L'ID du centre de santé.
     * @return Une liste des VaccinationDTO effectuées dans ce centre.
     */
    List<VaccinationDTO> getVaccinationByCentre(Long centreId);

}