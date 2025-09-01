package com.gestionvaccination.vaccinationservice.repository;

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

    List<Vaccination> getVaccinationByEnfantId(Long enfantId);
}
