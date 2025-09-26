package com.gestionvaccination.vaccinationservice.service.impl;

import com.gestionvaccination.vaccinationservice.client.dto.CentreDTO;
import com.gestionvaccination.vaccinationservice.client.dto.EnfantDTO;
import com.gestionvaccination.vaccinationservice.client.enumeration.Sexe;
import com.gestionvaccination.vaccinationservice.client.rest.LocationServiceClient;
import com.gestionvaccination.vaccinationservice.client.rest.UserServiceClient;
import com.gestionvaccination.vaccinationservice.entity.Vaccination;
import com.gestionvaccination.vaccinationservice.enumeration.StatutVaccination;
import com.gestionvaccination.vaccinationservice.repository.VaccinationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional

public class VaccinationStatsServiceImpl implements VaccinationStatsService{

    private final VaccinationRepository vaccinationRepository;
    private final UserServiceClient userServiceClient;
    private final LocationServiceClient locationServiceClient;

    @Override
    public Map<String, Long> getStatsByCentre(Long centreId) {

        // 1. Récupérer tous les enfants de ce centre depuis user-service
        List<EnfantDTO> enfantsDuCentre = userServiceClient.getEnfantsByCentre(centreId);

        // 2. Récupérer toutes les vaccinations dans vaccination-service pour ces enfants
        List<Long> enfantIds = enfantsDuCentre.stream()
                .map(EnfantDTO::getId)
                .collect(Collectors.toList());
        List<Vaccination> vaccinationsDuCentre = vaccinationRepository.findByEnfantIdIn(enfantIds);

        // 3. Calculer les statistiques
        long totalEnfants = enfantsDuCentre.size();
        long enfantsVaccines = vaccinationsDuCentre.stream()
                .filter(v -> v.getStatutVaccination() == StatutVaccination.EFFECTUER)
                .count();
        long totalGarcons = enfantsDuCentre.stream()
                .filter(e -> e.getSexe() == Sexe.MASCULIN)
                .count();
        long totalFilles = enfantsDuCentre.stream()
                .filter(e -> e.getSexe() == Sexe.FEMININ)
                .count();

        long garconsVaccines = vaccinationsDuCentre.stream()
                .filter(v -> v.getStatutVaccination() == StatutVaccination.EFFECTUER)
                .filter(v -> {
                    EnfantDTO enfant = enfantsDuCentre.stream()
                            .filter(e -> e.getId().equals(v.getEnfantId()))
                            .findFirst()
                            .orElse(null);
                    return enfant != null && enfant.getSexe() == Sexe.MASCULIN;
                })
                .count();
        long fillesVaccinees = enfantsVaccines - garconsVaccines;

        long garconsNonVaccines = totalGarcons - garconsVaccines;
        long fillesNonVaccinees = totalFilles - fillesVaccinees;

        Map<String, Long> stats = new HashMap<>();
        stats.put("totalEnfants", totalEnfants);
        stats.put("enfantsVaccines", enfantsVaccines);
        stats.put("garconsVaccines", garconsVaccines);
        stats.put("fillesVaccinees", fillesVaccinees);
        stats.put("garconsNonVaccines", garconsNonVaccines);
        stats.put("fillesNonVaccinees", fillesNonVaccinees);

        return stats;
    }



    @Override
    public long getNombreGarconsVaccines() {
        return getCountBySexe(Sexe.MASCULIN);
    }



    @Override
    public long getNombreFillesVaccinees() {
        return getCountBySexe(Sexe.FEMININ);
    }

    public long getNombreTotalEnfantsVaccines() {
        return vaccinationRepository.countByStatutVaccination(StatutVaccination.EFFECTUER);
    }



    @Override
    public long getCountBySexe(Sexe sexe) {
        return vaccinationRepository.findAll().stream()
                .filter(v -> v.getStatutVaccination() == StatutVaccination.EFFECTUER)
                .filter(v -> {
                    try {
                        EnfantDTO enfant = userServiceClient.getEnfantById(v.getEnfantId());
                        return enfant != null && enfant.getSexe() == sexe;
                    } catch (Exception e) {
                        // Gérer les erreurs (par exemple, si l'enfant n'existe pas)
                        return false;
                    }
                })
                .count();
    }




    /**
     * Calcule les statistiques de vaccination consolidées pour un district.
     * Cette méthode agrège les données de tous les centres de santé d'un district donné.
     *
     * @param districtId L'identifiant unique du district.
     * @return Une Map contenant les statistiques consolidées pour le district, avec une répartition
     * par centre de santé.
     */
    public Map<String, Object> getStatsByDistrict(Long districtId) {

        // 1. Récupérer tous les centres du district depuis le location-service
        List<CentreDTO> centresDuDistrict = locationServiceClient.getCentresByDistrict(districtId);

        Map<String, Object> districtStats = new HashMap<>();

        // 2. Calculer les statistiques pour chaque centre et les consolider
        for (CentreDTO centre : centresDuDistrict) {
            Map<String, Long> statsPourLeCentre = getStatsByCentre(centre.getId());
            districtStats.put(centre.getName(), statsPourLeCentre);
        }

        // 3. Calculer les totaux pour l'ensemble du district
        long totalEnfants = districtStats.values().stream()
                .mapToLong(stats -> (Long) ((Map<String, Long>) stats).get("totalEnfants"))
                .sum();
        long enfantsVaccines = districtStats.values().stream()
                .mapToLong(stats -> (Long) ((Map<String, Long>) stats).get("enfantsVaccines"))
                .sum();
        long garconsVaccines = districtStats.values().stream()
                .mapToLong(stats -> (Long) ((Map<String, Long>) stats).get("garconsVaccines"))
                .sum();
        long fillesVaccinees = districtStats.values().stream()
                .mapToLong(stats -> (Long) ((Map<String, Long>) stats).get("fillesVaccinees"))
                .sum();
        long garconsNonVaccines = districtStats.values().stream()
                .mapToLong(stats -> (Long) ((Map<String, Long>) stats).get("garconsNonVaccines"))
                .sum();
        long fillesNonVaccinees = districtStats.values().stream()
                .mapToLong(stats -> (Long) ((Map<String, Long>) stats).get("fillesNonVaccinees"))
                .sum();

        // 4. Ajouter les totaux au résultat
        Map<String, Long> totauxDistrict = new HashMap<>();
        totauxDistrict.put("totalEnfantsDistrict", totalEnfants);
        totauxDistrict.put("enfantsVaccinesDistrict", enfantsVaccines);
        totauxDistrict.put("garconsVaccinesDistrict", garconsVaccines);
        totauxDistrict.put("fillesVaccineesDistrict", fillesVaccinees);
        totauxDistrict.put("garconsNonVaccinesDistrict", garconsNonVaccines);
        totauxDistrict.put("fillesNonVaccineesDistrict", fillesNonVaccinees);

        districtStats.put("totauxDistrict", totauxDistrict);

        return districtStats;
    }







    @Override
    // Méthode manquante pour le comptage global
    public Map<String, Long> getComptageGlobal() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("nombreTotalEnfantsVaccines", getNombreTotalEnfantsVaccines());
        stats.put("nombreGarconsVaccines", getNombreGarconsVaccines());
        stats.put("nombreFillesVaccinees", getNombreFillesVaccinees());
        return stats;
    }
}
