package com.gestionvaccination.locationservice.enumeration;

/**
 * Énumération des types de commune
 */
public enum TypeCommune {
    URBAINE("Urbaine"),
    RURALE("Rurale");
    
    private final String libelle;
    
    TypeCommune(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
}
