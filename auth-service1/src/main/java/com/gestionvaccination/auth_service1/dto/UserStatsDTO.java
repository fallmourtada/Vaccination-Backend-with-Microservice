package com.gestionvaccination.auth_service1.dto;

/**
 * DTO pour les statistiques utilisateurs
 */
@lombok.Data
@lombok.Builder
public class UserStatsDTO {
    private long totalUsers;
    private boolean hasAdmin;
}
