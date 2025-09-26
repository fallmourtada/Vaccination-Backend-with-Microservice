package com.gestionvaccination.locationservice.service;

import com.gestionvaccination.locationservice.dto.CentreDTO;
import com.gestionvaccination.locationservice.dto.SaveCentreDTO;
import com.gestionvaccination.locationservice.dto.UpdateCentreDTO;

import java.util.List;

/**
 * Interface du service de gestion des centres de santé.
 * Définit les opérations CRUD (Créer, Lire, Mettre à jour, Supprimer) et de recherche
 * relatives aux centres de vaccination.
 */
public interface CentreService {

    /**
     * Récupère un centre de santé par son identifiant unique.
     *
     * @param centreId L'ID du centre de santé.
     * @return L'objet CentreDTO correspondant au centre.
     */
    public CentreDTO getCentreById(Long centreId);

    /**
     * Récupère la liste de tous les centres de santé enregistrés.
     *
     * @return Une liste d'objets CentreDTO.
     */
    public List<CentreDTO> getAllCentre();

    /**
     * Enregistre un nouveau centre de santé.
     *
     * @param saveCentreDTO Les données nécessaires à la création du centre.
     * @return L'objet CentreDTO du centre créé.
     */
    public CentreDTO saveCentre(SaveCentreDTO saveCentreDTO);

    /**
     * Supprime un centre de santé de la base de données.
     *
     * @param centreId L'ID du centre de santé à supprimer.
     */
    public void deleteCentreById(Long centreId);

    /**
     * Met à jour les informations d'un centre de santé existant.
     *
     * @param centreId L'ID du centre à mettre à jour.
     * @param updateCentreDTO Les nouvelles données à appliquer (nom, adresse, etc.).
     * @return L'objet CentreDTO du centre mis à jour.
     */
    public CentreDTO updateCentre(Long centreId, UpdateCentreDTO updateCentreDTO);

    /**
     * Récupère tous les centres de santé appartenant à un district spécifique.
     *
     * @param districtId L'ID du district.
     * @return Une liste d'objets CentreDTO situés dans ce district.
     */
    public List<CentreDTO> getCentresByDistrict(Long districtId);
}