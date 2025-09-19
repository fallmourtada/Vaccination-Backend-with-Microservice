package com.gestionvaccination.auth_service.controller;

import com.gestionvaccination.auth_service.dto.AuthRequestDTO;
import com.gestionvaccination.auth_service.enumeration.UserRole;
import com.gestionvaccination.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Contrôleur REST pour la gestion des utilisateurs
 */
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Gestion des Utilisateurs", description = "APIs pour la gestion des utilisateurs du système de vaccination")
@SecurityRequirement(name = "bearerAuth") // C'est ici que tu appliques la sécurité
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/Infirmier")
    @Operation(
            summary = "Créer un nouvel utilisateur",
            description = "Crée un nouvel utilisateur dans le système de vaccination"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Utilisateur existe déjà")
    })
    public ResponseEntity<Void> SaveInfirmier(
            @RequestBody AuthRequestDTO authRequestDTO) {

        authService.saveUser(authRequestDTO);
        return ResponseEntity.noContent().build();
    }



}
