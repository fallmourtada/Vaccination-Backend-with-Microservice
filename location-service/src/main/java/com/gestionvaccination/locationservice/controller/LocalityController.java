package com.gestionvaccination.locationservice.controller;

import com.gestionvaccination.locationservice.dto.LocalityDTO;
import com.gestionvaccination.locationservice.dto.SaveLocalityDTO;
import com.gestionvaccination.locationservice.dto.UpdateLocalityDTO;
import com.gestionvaccination.locationservice.service.LocalityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST principal pour la gestion des localités
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/localities")
@SecurityRequirement(name = "bearerAuth") // C'est ici que tu appliques la sécurité
@Tag(name = "Gestion des Localités", description = "API principale de gestion des localités géographiques")
public class LocalityController {


    private final LocalityService localityService;

    /**
     * Récupère une localité par son ID
     *
     * @param localityId l'ID de la localité à récupérer
     * @return la localité trouvée
     */
    @Operation(summary = "Récupérer une localité par ID", description = "Retourne une localité par son identifiant unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Localité trouvée",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = LocalityDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Localité non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{localityId}")
    public ResponseEntity<LocalityDTO> getById(
            @Parameter(description = "ID de la localité à récupérer", required = true)
            @PathVariable Long localityId) {
        return ResponseEntity.ok(localityService.getById(localityId));
    }



    /**
     * Crée une nouvelle localité
     *
     * @param saveLocalityDTO les données de la localité à créer
     * @return la localité créée
     */
    @Operation(summary = "Créer une nouvelle localité", description = "Crée une nouvelle localité avec les données fournies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Localité créée avec succès",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = LocalityDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping
    public ResponseEntity<LocalityDTO> create(
            @Parameter(description = "Données de la localité à créer", required = true)
            @RequestBody SaveLocalityDTO saveLocalityDTO) {
        return new ResponseEntity<>(localityService.create(saveLocalityDTO), HttpStatus.CREATED);
    }



    /**
     * Met à jour une localité existante
     *
     * @param id l'ID de la localité à mettre à jour
     * @param updateLocalityDTO les nouvelles données de la localité
     * @return la localité mise à jour
     */
    @Operation(summary = "Mettre à jour une localité", description = "Met à jour une localité existante avec les données fournies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Localité mise à jour avec succès",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = LocalityDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Localité non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LocalityDTO> update(
            @Parameter(description = "ID de la localité à mettre à jour", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nouvelles données de la localité", required = true)
            @RequestBody UpdateLocalityDTO updateLocalityDTO) {
        return ResponseEntity.ok(localityService.update(id, updateLocalityDTO));
    }



    /**
     * Supprime une localité par son ID
     *
     * @param id l'ID de la localité à supprimer
     * @return réponse sans contenu
     */
    @Operation(summary = "Supprimer une localité", description = "Supprime une localité par son identifiant unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Localité supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Localité non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID de la localité à supprimer", required = true)
            @PathVariable Long id) {
        localityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }




    /**
     Nouvelle Methode Pour  Récupérer tous les IDs de communes descendantes
     que jai besoin dans community-service pour avoir la contribution total
     par region departement commune etc---

     */
    @Operation(summary = "Récupérer les IDs de toutes les communes descendantes",
            description = "Retourne une liste d'IDs de toutes les communes qui sont des enfants (directs ou indirects) de la localité spécifiée.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des IDs de communes récupérée avec succès",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(type = "integer", format = "int64"))) }),
            @ApiResponse(responseCode = "404", description = "Localité parente non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/children-communes")
    public ResponseEntity<List<Long>> getAllChildCommuneIds(
            @Parameter(description = "ID de la localité parente (Région, Département, Arrondissement, Commune)", required = true)
            @RequestParam("parentLocalityId") Long parentLocalityId) {
        return ResponseEntity.ok(localityService.getCommuneIdsDescendants(parentLocalityId));
    }



}
