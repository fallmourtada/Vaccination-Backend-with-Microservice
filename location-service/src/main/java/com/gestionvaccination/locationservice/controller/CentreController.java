package com.gestionvaccination.locationservice.controller;

import com.gestionvaccination.locationservice.dto.CentreDTO;
import com.gestionvaccination.locationservice.dto.SaveCentreDTO;
import com.gestionvaccination.locationservice.dto.UpdateCentreDTO;
import com.gestionvaccination.locationservice.service.CentreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * Contrôleur REST pour la gestion des centres de vaccination
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/localities/centres")
@SecurityRequirement(name = "bearerAuth") // C'est ici que tu appliques la sécurité
@Tag(name = "Gestion des Centres", description = "API pour gérer les centres de vaccination")
public class CentreController {

    private final CentreService centreService;

    /**
     * Récupère un centre par son ID
     *
     * @param centreId l'ID du centre à récupérer
     * @return le centre trouvé
     */
    @Operation(summary = "Récupérer un centre par ID", description = "Retourne un centre de vaccination par son identifiant unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Centre trouvé",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CentreDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Centre non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{centreId}")
    public ResponseEntity<CentreDTO> getById(
            @Parameter(description = "ID du centre à récupérer", required = true)
            @PathVariable Long centreId) {
        return ResponseEntity.ok(centreService.getCentreById(centreId));
    }


    /**
     * Récupère la liste de tous les centres
     *
     * @return la liste de tous les centres
     */
    @Operation(summary = "Récupérer tous les centres", description = "Retourne la liste de tous les centres de vaccination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des centres récupérée avec succès",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CentreDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("")
    public ResponseEntity<List<CentreDTO>> getAll() {
        return ResponseEntity.ok(centreService.getAllCentre());
    }




    /**
     * Endpoint pour récupérer la liste des centres d'un district.
     * @param districtId L'identifiant du district.
     * @return La liste des centres.
     */
    @GetMapping("/by-district/{districtId}")
    @Operation(summary = "Récupérer les centres d'un district",
            description = "Retourne la liste de tous les centres de santé rattachés à un district donné.")
    public List<CentreDTO> getCentresByDistrict(@PathVariable Long districtId) {
        return centreService.getCentresByDistrict(districtId);
    }



    /**
     * Crée un nouveau centre
     *
     * @param saveCentreDTO les données du centre à créer
     * @return le centre créé
     */
    @Operation(summary = "Créer un nouveau centre", description = "Crée un nouveau centre de vaccination avec les données fournies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Centre créé avec succès",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CentreDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping
    public ResponseEntity<CentreDTO> create(
            @Parameter(description = "Données du centre à créer", required = true)
            @RequestBody @Valid SaveCentreDTO saveCentreDTO) {
        return new ResponseEntity<>(centreService.saveCentre(saveCentreDTO), HttpStatus.CREATED);
    }


    
    /**
     * Met à jour un centre existant
     *
     * @param id l'ID du centre à mettre à jour
     * @param updateCentreDTO les nouvelles données du centre
     * @return le centre mis à jour
     */
    @Operation(summary = "Mettre à jour un centre", description = "Met à jour un centre existant avec les données fournies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Centre mis à jour avec succès",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CentreDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Centre non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CentreDTO> update(
            @Parameter(description = "ID du centre à mettre à jour", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nouvelles données du centre", required = true)
            @RequestBody @Valid UpdateCentreDTO updateCentreDTO) {
        return ResponseEntity.ok(centreService.updateCentre(id, updateCentreDTO));
    }


    /**
     * Supprime un centre par son ID
     *
     * @param id l'ID du centre à supprimer
     * @return réponse sans contenu
     */
    @Operation(summary = "Supprimer un centre", description = "Supprime un centre par son identifiant unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Centre supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Centre non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID du centre à supprimer", required = true)
            @PathVariable Long id) {
        centreService.deleteCentreById(id);
        return ResponseEntity.noContent().build();
    }
}