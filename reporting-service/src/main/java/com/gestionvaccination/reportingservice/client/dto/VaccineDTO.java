package com.gestionvaccination.reportingservice.client.dto;

import java.time.LocalDate;
import java.util.List;

public class VaccineDTO {
    private Long id;
    private String nom;
    private Long localityId;
    // ... other fields omitted

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Long getLocalityId() { return localityId; }
    public void setLocalityId(Long localityId) { this.localityId = localityId; }
}
