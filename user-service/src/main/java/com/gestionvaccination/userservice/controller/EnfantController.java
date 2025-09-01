package com.gestionvaccination.userservice.controller;

import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.gestionvaccination.userservice.dto.*;
import com.gestionvaccination.userservice.services.EnfantService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des enfants
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users/enfants")
@Tag(name = "Gestion des Enfants", description = "APIs pour la gestion des enfants dans le système de vaccination")
@Validated
@AllArgsConstructor
public class EnfantController {
    private final EnfantService enfantService;

    @PostMapping
    @Operation(
            summary = "Créer un nouvel enfant",
            description = "Enregistre un nouvel enfant dans le système de vaccination"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Enfant créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Parent non trouvé")
    })
    public ResponseEntity<EnfantDTO> creerEnfant(
            @RequestBody SaveEnfantDTO saveEnfantDTO,@RequestParam Long parentId) throws IOException, WriterException {

        EnfantDTO enfant = enfantService.saveEnfant(saveEnfantDTO,parentId);
        return new ResponseEntity<>(enfant, HttpStatus.CREATED);
    }





    @GetMapping("/{id}")
    @Operation(
            summary = "Obtenir un enfant par ID",
            description = "Récupère les détails d'un enfant spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enfant trouvé"),
            @ApiResponse(responseCode = "404", description = "Enfant non trouvé")
    })
    public ResponseEntity<EnfantDTO> obtenirEnfantParId(
            @Parameter(description = "ID de l'enfant") @PathVariable Long id) {

        EnfantDTO enfant = enfantService.getEnfantById(id);
        return ResponseEntity.ok(enfant);
    }





    @GetMapping("/qr/{codeQr}")
    @Operation(
            summary = "Obtenir un enfant par code QR",
            description = "Récupère les détails d'un enfant grâce à son code QR"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enfant trouvé"),
            @ApiResponse(responseCode = "404", description = "Enfant non trouvé avec ce code QR")
    })
    public ResponseEntity<EnfantDTO> obtenirEnfantParCodeQr(
            @Parameter(description = "Code QR de l'enfant") @PathVariable String codeQr) {

        EnfantDTO enfant = enfantService.getEnfantByCodeQr(codeQr);
        return ResponseEntity.ok(enfant);
    }





    @PutMapping("/{id}")
    @Operation(
            summary = "Mettre à jour un enfant",
            description = "Met à jour les informations d'un enfant existant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enfant mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Enfant non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<EnfantDTO> mettreAJourEnfant(
            @Parameter(description = "ID de l'enfant") @PathVariable Long id,
            @Valid @RequestBody UpdateEnfantDTO updateEnfantDTO) {

        EnfantDTO enfant = enfantService.updateEnfant(id, updateEnfantDTO);
        return ResponseEntity.ok(enfant);
    }





    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer un enfant",
            description = "Supprime un enfant du système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Enfant supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Enfant non trouvé")
    })
    public ResponseEntity<Void> supprimerEnfant(
            @Parameter(description = "ID de l'enfant") @PathVariable Long id) {

        enfantService.deleteEnfant(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/parent/{parentId}")
//    @Operation(
//        summary = "Obtenir les enfants d'un parent",
//        description = "Récupère tous les enfants d'un parent spécifique"
//    )
//    @ApiResponse(responseCode = "200", description = "Liste des enfants du parent")
//    public ResponseEntity<List<EnfantDTO>> obtenirEnfantsParParent(
//            @Parameter(description = "ID du parent") @PathVariable Long parentId) {
//
//        List<EnfantDTO> enfants = enfantService.getEnfantByParentId(parentId);
//        return ResponseEntity.ok(enfants);
//    }

}