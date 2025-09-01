package com.gestionvaccination.locationservice.enumeration;

/**
 * Les différents types de localités pour le Sénégal
 * Hiérarchie: REGION -> DEPARTMENT -> COMMUNE -> NEIGHBORHOOD
 */
public enum LocalityType {
    REGION,       // Région administrative (14 régions au Sénégal)
    DEPARTMENT,   // Département (45 départements)
    DISTRICT,     // Arrondissement (optionnel, pour certaines grandes villes)
    COMMUNE,      // Commune ou ville
    NEIGHBORHOOD  // Quartier ou secteur
}
