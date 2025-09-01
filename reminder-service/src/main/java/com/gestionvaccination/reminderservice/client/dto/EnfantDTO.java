package com.gestionvaccination.reminderservice.client.dto;

import java.time.LocalDate;

/**
 * DTO simplifié pour représenter un enfant provenant du user-service
 */
public class EnfantDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
    private Long localityId;

    // getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public Long getLocalityId() { return localityId; }
    public void setLocalityId(Long localityId) { this.localityId = localityId; }
}
