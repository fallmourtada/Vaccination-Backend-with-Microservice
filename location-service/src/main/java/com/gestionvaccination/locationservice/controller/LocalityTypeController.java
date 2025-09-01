package com.gestionvaccination.locationservice.controller;

import com.gestionvaccination.locationservice.dto.LocalityDTO;
import com.gestionvaccination.locationservice.dto.SaveLocalityDTO;
import com.gestionvaccination.locationservice.enumeration.LocalityType;
import com.gestionvaccination.locationservice.service.LocalityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST spécialisé pour la gestion par types de localités
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/localities/types")
@Tag(name = "Types de Localités", description = "API pour la gestion des différents types de localités (régions, départements, arrondissements, communes, quartiers)")
public class LocalityTypeController {

    private final LocalityService localityService;

    // ============= RÉGIONS =============
    
    @GetMapping("/regions")
    @Operation(summary = "Récupérer toutes les régions", description = "Retourne la liste de toutes les régions du Sénégal")
    @ApiResponse(responseCode = "200", description = "Liste des régions récupérée avec succès",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LocalityDTO.class))))
    public ResponseEntity<List<LocalityDTO>> getAllRegions() {
        return ResponseEntity.ok(localityService.getAllRegions());
    }

    @PostMapping("/regions")
    @Operation(summary = "Créer une nouvelle région", description = "Crée une nouvelle région")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Région créée avec succès",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocalityDTO.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<LocalityDTO> createRegion(
            @Parameter(description = "Données de la région à créer", required = true)
            @Valid @RequestBody SaveLocalityDTO saveLocalityDTO) {
        saveLocalityDTO.setType(LocalityType.REGION);
        saveLocalityDTO.setParentId(null); // Une région n'a pas de parent
        return new ResponseEntity<>(localityService.create(saveLocalityDTO), HttpStatus.CREATED);
    }

    // ============= DÉPARTEMENTS =============
    
    @GetMapping("/departments")
    @Operation(summary = "Récupérer tous les départements", description = "Retourne la liste de tous les départements")
    @ApiResponse(responseCode = "200", description = "Liste des départements récupérée avec succès",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LocalityDTO.class))))
    public ResponseEntity<List<LocalityDTO>> getAllDepartments() {
        return ResponseEntity.ok(localityService.getAllDepartments());
    }

    @GetMapping("/regions/{regionId}/departments")
    @Operation(summary = "Récupérer les départements d'une région", description = "Retourne la liste des départements appartenant à une région spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des départements récupérée avec succès",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = LocalityDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Région non trouvée")
    })
    public ResponseEntity<List<LocalityDTO>> getDepartmentsByRegion(
            @Parameter(description = "ID de la région", required = true)
            @PathVariable Long regionId) {
        return ResponseEntity.ok(localityService.getDepartmentsByRegion(regionId));
    }

    @PostMapping("/regions/{regionId}/departments")
    @Operation(summary = "Créer un nouveau département", description = "Crée un nouveau département dans une région spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Département créé avec succès",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocalityDTO.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Région parente non trouvée")
    })
    public ResponseEntity<LocalityDTO> createDepartment(
            @Parameter(description = "ID de la région parente", required = true)
            @PathVariable Long regionId,
            @Parameter(description = "Données du département à créer", required = true)
            @Valid @RequestBody SaveLocalityDTO saveLocalityDTO) {
        saveLocalityDTO.setType(LocalityType.DEPARTMENT);
        saveLocalityDTO.setParentId(regionId);
        return new ResponseEntity<>(localityService.create(saveLocalityDTO), HttpStatus.CREATED);
    }

    // ============= ARRONDISSEMENTS (DISTRICTS) =============
    
    @GetMapping("/districts")
    @Operation(summary = "Récupérer tous les arrondissements", description = "Retourne la liste de tous les arrondissements")
    @ApiResponse(responseCode = "200", description = "Liste des arrondissements récupérée avec succès",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LocalityDTO.class))))
    public ResponseEntity<List<LocalityDTO>> getAllDistricts() {
        return ResponseEntity.ok(localityService.getAllDistricts());
    }

    @GetMapping("/departments/{departmentId}/districts")
    @Operation(summary = "Récupérer les arrondissements d'un département", description = "Retourne la liste des arrondissements appartenant à un département spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des arrondissements récupérée avec succès",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = LocalityDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Département non trouvé")
    })
    public ResponseEntity<List<LocalityDTO>> getDistrictsByDepartment(
            @Parameter(description = "ID du département", required = true)
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(localityService.getDistrictsByDepartment(departmentId));
    }

    @PostMapping("/departments/{departmentId}/districts")
    @Operation(summary = "Créer un nouvel arrondissement", description = "Crée un nouvel arrondissement dans un département spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Arrondissement créé avec succès",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocalityDTO.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Département parent non trouvé")
    })
    public ResponseEntity<LocalityDTO> createDistrict(
            @Parameter(description = "ID du département parent", required = true)
            @PathVariable Long departmentId,
            @Parameter(description = "Données de l'arrondissement à créer", required = true)
            @Valid @RequestBody SaveLocalityDTO saveLocalityDTO) {
        saveLocalityDTO.setType(LocalityType.DISTRICT);
        saveLocalityDTO.setParentId(departmentId);
        return new ResponseEntity<>(localityService.create(saveLocalityDTO), HttpStatus.CREATED);
    }

    // ============= COMMUNES =============
    
    @GetMapping("/communes")
    @Operation(summary = "Récupérer toutes les communes", description = "Retourne la liste de toutes les communes")
    @ApiResponse(responseCode = "200", description = "Liste des communes récupérée avec succès",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LocalityDTO.class))))
    public ResponseEntity<List<LocalityDTO>> getAllCommunes() {
        return ResponseEntity.ok(localityService.getAllCommunes());
    }

    @GetMapping("/districts/{districtId}/communes")
    @Operation(summary = "Récupérer les communes d'un arrondissement", description = "Retourne la liste des communes appartenant à un arrondissement spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des communes récupérée avec succès",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = LocalityDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Arrondissement non trouvé")
    })
    public ResponseEntity<List<LocalityDTO>> getCommunesByDistrict(
            @Parameter(description = "ID de l'arrondissement", required = true)
            @PathVariable Long districtId) {
        return ResponseEntity.ok(localityService.getCommunesByDistrict(districtId));
    }

    @PostMapping("/districts/{districtId}/communes")
    @Operation(summary = "Créer une nouvelle commune", description = "Crée une nouvelle commune dans un arrondissement spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Commune créée avec succès",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocalityDTO.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Arrondissement parent non trouvé")
    })
    public ResponseEntity<LocalityDTO> createCommuneInDistrict(
            @Parameter(description = "ID de l'arrondissement parent", required = true)
            @PathVariable Long districtId,
            @Parameter(description = "Données de la commune à créer", required = true)
            @Valid @RequestBody SaveLocalityDTO saveLocalityDTO) {
        saveLocalityDTO.setType(LocalityType.COMMUNE);
        saveLocalityDTO.setParentId(districtId);
        return new ResponseEntity<>(localityService.create(saveLocalityDTO), HttpStatus.CREATED);
    }

    // ============= COMMUNES DIRECTEMENT DANS DÉPARTEMENTS =============
    
    @GetMapping("/departments/{departmentId}/communes")
    @Operation(summary = "Récupérer les communes d'un département", description = "Retourne la liste des communes appartenant directement à un département spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des communes récupérée avec succès",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = LocalityDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Département non trouvé")
    })
    public ResponseEntity<List<LocalityDTO>> getCommunesByDepartment(
            @Parameter(description = "ID du département", required = true)
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(localityService.getCommunesByDepartment(departmentId));
    }

    @PostMapping("/departments/{departmentId}/communes")
    @Operation(summary = "Créer une nouvelle commune", description = "Crée une nouvelle commune directement dans un département spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Commune créée avec succès",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocalityDTO.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Département parent non trouvé")
    })
    public ResponseEntity<LocalityDTO> createCommuneInDepartment(
            @Parameter(description = "ID du département parent", required = true)
            @PathVariable Long departmentId,
            @Parameter(description = "Données de la commune à créer", required = true)
            @Valid @RequestBody SaveLocalityDTO saveLocalityDTO) {
        saveLocalityDTO.setType(LocalityType.COMMUNE);
        saveLocalityDTO.setParentId(departmentId);
        return new ResponseEntity<>(localityService.create(saveLocalityDTO), HttpStatus.CREATED);
    }

    // ============= COMMUNES PAR RÉGION =============
    
    @GetMapping("/regions/{regionId}/communes")
    @Operation(summary = "Récupérer les communes d'une région", description = "Retourne la liste des communes appartenant à une région spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des communes récupérée avec succès",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = LocalityDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Région non trouvée")
    })
    public ResponseEntity<List<LocalityDTO>> getCommunesByRegion(
            @Parameter(description = "ID de la région", required = true)
            @PathVariable Long regionId) {
        return ResponseEntity.ok(localityService.getCommunesByRegion(regionId));
    }

    // ============= QUARTIERS =============
    
    @GetMapping("/communes/{communeId}/neighborhoods")
    @Operation(summary = "Récupérer tous les quartiers d'une commune", description = "Retourne la liste des quartiers appartenant à une commune spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des quartiers récupérée avec succès",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = LocalityDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Commune non trouvée")
    })
    public ResponseEntity<List<LocalityDTO>> getNeighborhoodsByCommune(
            @Parameter(description = "ID de la commune", required = true)
            @PathVariable Long communeId) {
        return ResponseEntity.ok(localityService.getAllByTypeAndParent(LocalityType.NEIGHBORHOOD, communeId));
    }

    @PostMapping("/communes/{communeId}/neighborhoods")
    @Operation(summary = "Créer un nouveau quartier", description = "Crée un nouveau quartier dans une commune spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Quartier créé avec succès",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocalityDTO.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Commune parente non trouvée")
    })
    public ResponseEntity<LocalityDTO> createNeighborhood(
            @Parameter(description = "ID de la commune parente", required = true)
            @PathVariable Long communeId,
            @Parameter(description = "Données du quartier à créer", required = true)
            @Valid @RequestBody SaveLocalityDTO saveLocalityDTO) {
        saveLocalityDTO.setType(LocalityType.NEIGHBORHOOD);
        saveLocalityDTO.setParentId(communeId);
        return new ResponseEntity<>(localityService.create(saveLocalityDTO), HttpStatus.CREATED);
    }
}
