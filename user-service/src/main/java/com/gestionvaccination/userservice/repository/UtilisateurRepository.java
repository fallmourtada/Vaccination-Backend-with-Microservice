package com.gestionvaccination.userservice.repository;

import com.gestionvaccination.userservice.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la gestion des utilisateurs
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByEmail(String email);


    //Ajoutez une nouvelle méthode pour trouver les utilisateurs (parents) par centre.
    List<Utilisateur> findByCentreId(Long centreId);
}
