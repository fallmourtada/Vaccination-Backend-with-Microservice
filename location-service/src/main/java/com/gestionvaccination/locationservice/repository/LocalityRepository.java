package com.gestionvaccination.locationservice.repository;

import com.gestionvaccination.locationservice.entity.Locality;
import com.gestionvaccination.locationservice.enumeration.LocalityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour les opérations de base de données sur les localités
 */
@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {
    
    /**
     * Trouve toutes les localités par type
     */
    List<Locality> findAllByType(LocalityType type);
    
    /**
     * Trouve toutes les localités enfants d'un parent donné
     */
    List<Locality> findAllByParentId(Long parentId);
    
    /**
     * Trouve toutes les localités d'un type donné ayant un parent spécifique
     */
    List<Locality> findAllByTypeAndParentId(LocalityType type, Long parentId);
    
    /**
     * Trouve toutes les localités d'un type donné sans parent (niveau racine)
     */
    @Query("SELECT l FROM Locality l WHERE l.type = :type AND l.parent IS NULL")
    List<Locality> findAllByTypeWithNoParent(@Param("type") LocalityType type);
    
    /**
     * Trouve une localité par nom et type
     */
    Optional<Locality> findByNameAndType(String name, LocalityType type);

    /**
     * Trouve toutes les communes d'une région donnée
     * Hiérarchie: Région -> Département -> Commune
     */
    @Query("SELECT c FROM Locality c " +
            "WHERE c.type = 'COMMUNE' " +
            "AND c.parent.type = 'DEPARTMENT' " +
            "AND c.parent.parent.id = :regionId")
    List<Locality> findCommunesByRegionId(@Param("regionId") Long regionId);

    /**
     * Trouve toutes les localités actives
     */
    List<Locality> findAllByActiveTrue();
    
    /**
     * Trouve toutes les localités actives d'un type donné
     */
    List<Locality> findAllByTypeAndActiveTrue(LocalityType type);
    
    /**
     * Trouve toutes les localités par nom (recherche insensible à la casse)
     */
    @Query("SELECT l FROM Locality l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Locality> findByNameContainingIgnoreCase(@Param("name") String name);

    /**
     * Trouve toutes les données pour construire l'arbre complet
     */
    List<Locality> findAll();
}
