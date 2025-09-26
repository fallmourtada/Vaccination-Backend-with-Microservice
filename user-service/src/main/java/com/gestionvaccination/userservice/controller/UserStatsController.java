package com.gestionvaccination.userservice.controller;

import com.gestionvaccination.userservice.dto.EnfantDTO;
import com.gestionvaccination.userservice.entites.Enfant;
import com.gestionvaccination.userservice.services.UserStatsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/stats")
@Tag(name = "Gestion des Utilisateurs", description = "APIs pour la gestion des utilisateurs du système de vaccination")
@SecurityRequirement(name = "bearerAuth") // C'est ici que tu appliques la sécurité
@AllArgsConstructor
public class UserStatsController {

    private final UserStatsService userStatsService;


    @GetMapping("/by-centre/{centreId}/enfants")
    public List<EnfantDTO> getEnfantsByCentre(@PathVariable Long centreId) {
        return userStatsService.getEnfantsByCentre(centreId);
    }
}
