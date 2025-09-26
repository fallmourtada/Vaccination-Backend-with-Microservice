package com.gestionvaccination.locationservice.repository;

import com.gestionvaccination.locationservice.entity.Centre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CentreRepository extends JpaRepository<Centre, Long> {

    /**
     * Récupère une liste de centres de santé enfants en fonction de l'identifiant de leur centre parent (district).
     * @param parentId L'identifiant du centre parent (district).
     * @return Une liste des centres de santé enfants.
     */
    List<Centre> findByParentId(Long parentId);
}
