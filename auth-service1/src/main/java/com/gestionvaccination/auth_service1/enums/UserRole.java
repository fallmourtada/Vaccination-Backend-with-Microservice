package com.gestionvaccination.auth_service1.enums;

public enum UserRole {
    ICP("Infirmier Chef de Poste"),
    SAGE_FEMME("Professionnel(le) spécialisé(e) en suivi de grossesse et accouchement"),
    INFIRMIER("Personnel infirmier en charge des soins et du suivi des patients"),
    ADMIN("Administrateur du système, gère les accès et la configuration"),
    PARENT("Parent ou tuteur, utilisateur final pour suivi/consultation");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
