package com.gestionvaccination.vaccinationservice.repository;

import com.gestionvaccination.vaccinationservice.enumeration.StatutVaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestionvaccination.vaccinationservice.entity.Vaccination;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour les opérations CRUD sur l'entité Vaccination
 */
@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {

    /**
     * Récupère toutes les vaccinations pour un enfant spécifique en utilisant son identifiant.
     * @param enfantId L'identifiant de l'enfant.
     * @return Une liste de vaccinations associées à l'enfant.
     */
    List<Vaccination> findByEnfantId(Long enfantId);

    /**
     * Compte le nombre total de vaccinations ayant un statut donné.
     * Cette méthode est utilisée pour les statistiques globales sur les vaccinations effectuées.
     * @param statutVaccination Le statut de la vaccination (EFFECTUER, NON_EFFECTUER, etc.).
     * @return Le nombre de vaccinations correspondant au statut.
     */
    long countByStatutVaccination(StatutVaccination statutVaccination);

    /**
     * Récupère une liste de vaccinations pour un ensemble d'identifiants d'enfants.
     * Cette méthode est utile pour récupérer en une seule requête les vaccinations d'un groupe d'enfants
     * (par exemple, tous les enfants d'un même centre de santé).
     * @param enfantIds Une liste d'identifiants d'enfants.
     * @return Une liste de vaccinations correspondant aux identifiants d'enfants fournis.
     */
    List<Vaccination> findByEnfantIdIn(List<Long> enfantIds);


    List<Vaccination> findByParentId(Long parentId);

}
