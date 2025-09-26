package com.gestionvaccination.userservice.services;

import com.gestionvaccination.userservice.dto.EnfantDTO;
import com.gestionvaccination.userservice.entites.Enfant;

import java.util.List;

public interface UserStatsService {

    /**
     * Récupère une liste d'enfants rattachés à un centre de santé spécifique.
     * Cette méthode est utilisée pour filtrer les enfants par leur centre de vaccination.
     *
     * @param centreId L'identifiant unique du centre de santé.
     * @return Une liste de {@link EnfantDTO} représentant les enfants du centre.
     */
    List<EnfantDTO> getEnfantsByCentre(Long centreId);
}
