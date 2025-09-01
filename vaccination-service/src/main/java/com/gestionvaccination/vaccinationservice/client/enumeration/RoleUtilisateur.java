package com.gestionvaccination.vaccinationservice.client.enumeration;

/**
 * Énumération des différents rôles d'utilisateur dans le système de vaccination
 */
public enum RoleUtilisateur {
    PARENT("Parent"),
    MEDECIN("Médecin"),
    ADMINISTRATEUR("Administrateur"),
    AGENT_SANTE("Agent de Santé"),
    GESTIONNAIRE_CENTRE("Gestionnaire de Centre"),
    SAGE_FEMME("Sage-femme"),
    INFIRMIER("Infirmier/Infirmière"),
    SUPERVISEUR("Superviseur");
    
    private final String libelle;
    
    RoleUtilisateur(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
}
