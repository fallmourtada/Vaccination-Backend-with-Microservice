package com.gestionvaccination.vaccineservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gestionvaccination.vaccineservice.entity.Vaccine;
import com.gestionvaccination.vaccineservice.enumeration.VaccineType;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository pour les opérations CRUD sur l'entité Vaccine
 */
@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    
    /**
     * Vérifier si un vaccin existe par son nom
     */
    boolean existsByNom(String nom);
    
    /**
     * Vérifier si un vaccin existe par son numéro de lot
     */
    boolean existsByNumeroLot(String numeroLot);
    
//    /**
//     * Trouver un vaccin par son nom
//     */
//    List<Vaccine> trouverParNomContenant(String nom);
//
//    /**
//     * Trouver des vaccins par fabricant
//     */
//    List<Vaccine> trouverParFabricantContenant(String fabricant);
//
//    /**
//     * Trouver des vaccins par type
//     */
//    List<Vaccine> trouverParTypeVaccin(VaccineType typeVaccin);
//
//    /**
//     * Trouver des vaccins qui expirent avant une date donnée
//     */
//    List<Vaccine> trouverParDateExpirationAvant(LocalDate date);
//
//    /**
//     * Trouver des vaccins par localité
//     */
//    List<Vaccine> trouverParLocaliteId(Long localiteId);
//
//    /**
//     * Trouver des vaccins avec une quantité disponible > 0
//     */
//    @Query("SELECT v FROM Vaccine v WHERE v.quantiteDisponible > 0")
//    List<Vaccine> trouverVaccinsDisponibles();
//
//    /**
//     * Trouver des vaccins adaptés à un âge donné (en mois)
//     */
//    @Query("SELECT v FROM Vaccine v WHERE :ageMois BETWEEN v.ageMinimumMois AND COALESCE(v.ageMaximumMois, 999)")
//    List<Vaccine> trouverVaccinsPourAge(@Param("ageMois") int ageMois);
//
//    /**
//     * Recherche de vaccins par différents critères
//     */
//    @Query("SELECT v FROM Vaccine v WHERE " +
//           "(:nom IS NULL OR LOWER(v.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
//           "(:fabricant IS NULL OR LOWER(v.fabricant) LIKE LOWER(CONCAT('%', :fabricant, '%'))) AND " +
//           "(:typeVaccin IS NULL OR v.typeVaccin = :typeVaccin) AND " +
//           "(:disponible IS NULL OR (:disponible = true AND v.quantiteDisponible > 0) OR (:disponible = false))")
//    Page<Vaccine> rechercherVaccins(
//            @Param("nom") String nom,
//            @Param("fabricant") String fabricant,
//            @Param("typeVaccin") VaccineType typeVaccin,
//            @Param("disponible") Boolean disponible,
//            Pageable pageable);
}
