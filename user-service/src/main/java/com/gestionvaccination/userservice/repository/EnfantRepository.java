package com.gestionvaccination.userservice.repository;

import com.gestionvaccination.userservice.entites.Enfant;
import com.gestionvaccination.userservice.enumeration.Sexe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des enfants
 */
@Repository
public interface EnfantRepository extends JpaRepository<Enfant, Long> {
    Optional<Enfant> findByContenuQrCode(String contenuQrCode);

    Optional<Enfant> findByAccessToken(String accessToken);

    // Compte le nombre d'enfants par sexe
    long countBySexe(Sexe sexe);

}