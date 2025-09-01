package com.gestionvaccination.appointmentservice.client.enumeration;

/**
 * Énumération des statuts d'utilisateur
 */
public enum StatutUtilisateur {
    ACTIF("Actif"),
    INACTIF("Inactif"),
    SUSPENDU("Suspendu"),
    EN_ATTENTE_VERIFICATION("En attente de vérification"),
    BLOQUE("Bloqué"),
    ARCHIVE("Archivé");
    
    private final String libelle;
    
    StatutUtilisateur(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
}
