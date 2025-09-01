package com.gestionvaccination.userservice.enumeration;

/**
 * Énumération du sexe
 */
public enum Sexe {
    MASCULIN("Masculin"),
    FEMININ("Féminin");
    
    private final String libelle;
    
    Sexe(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
}
