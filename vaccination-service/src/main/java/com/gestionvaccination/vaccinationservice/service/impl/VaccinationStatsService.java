package com.gestionvaccination.vaccinationservice.service.impl;

import com.gestionvaccination.vaccinationservice.client.enumeration.Sexe;

import java.util.Map;

public interface VaccinationStatsService {

    /**
     * Récupère les statistiques de vaccination pour un centre de santé spécifique.
     * Inclut le nombre total d'enfants, les enfants vaccinés, et leur répartition par sexe et statut.
     * @param centreId L'identifiant unique du centre de santé.
     * @return Une Map contenant les statistiques détaillées pour le centre.
     */
    Map<String, Long> getStatsByCentre(Long centreId);


    /**
     * Récupère les statistiques globales de vaccination, y compris le nombre total d'enfants vaccinés
     * et la répartition par sexe.
     * @return Une Map contenant les statistiques globales : nombreTotalEnfantsVaccines, nombreGarconsVaccines, nombreFillesVaccinees.
     */
    Map<String, Long> getComptageGlobal();


    /**
     * Calcule le nombre total de garçons ayant reçu une vaccination avec un statut "EFFECTUER".
     * @return Le nombre de garçons vaccinés.
     */
    long getNombreGarconsVaccines();


    /**
     * Calcule le nombre total de filles ayant reçu une vaccination avec un statut "EFFECTUER".
     * @return Le nombre de filles vaccinées.
     */
    long getNombreFillesVaccinees();


    /**
     * Calcule le nombre total de vaccinations ayant un statut "EFFECTUER".
     * @return Le nombre total de vaccinations effectuées.
     */
    long getNombreTotalEnfantsVaccines();


    /**
     * Méthode privée utilitaire pour compter les vaccinations par sexe et statut.
     * Elle interroge le microservice 'user-service' pour obtenir le sexe de chaque enfant.
     * @param sexe Le sexe de l'enfant (MASCULIN ou FEMININ).
     * @return Le nombre de vaccinations correspondant aux critères.
     */
    long getCountBySexe(Sexe sexe);



    /**
     * Calcule les statistiques de vaccination consolidées pour un district.
     * Cette méthode agrège les données de tous les centres de santé d'un district donné.
     *
     * @param districtId L'identifiant unique du district.
     * @return Une Map contenant les statistiques consolidées pour le district, avec une répartition
     * par centre de santé.
     */

    Map<String, Object> getStatsByDistrict(Long districtId);
}
