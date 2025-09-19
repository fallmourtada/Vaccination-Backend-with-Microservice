package com.gestionvaccination.vaccineservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gestionvaccination.vaccineservice.dto.VaccineDTO;
import com.gestionvaccination.vaccineservice.dto.SaveVaccineDTO;
import com.gestionvaccination.vaccineservice.dto.UpdateVaccineDTO;
import com.gestionvaccination.vaccineservice.enumeration.VaccineType;
import com.gestionvaccination.vaccineservice.service.VaccineService;

import java.time.LocalDate;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des vaccins
 */
@RestController
@RequestMapping("/api/v1/vaccines")
@AllArgsConstructor
@Tag(name = "Vaccine API", description = "API pour la gestion des vaccins")
@SecurityRequirement(name = "bearerAuth") // C'est ici que tu appliques la sécurité
public class VaccineController {

    private final VaccineService vaccineService;

    /**
     * Créer un nouveau vaccin
     */
    @PostMapping
    @Operation(summary = "Créer un nouveau vaccin")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vaccin créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données de vaccin invalides")
    })
    public ResponseEntity<VaccineDTO> creerVaccin(@Valid @RequestBody SaveVaccineDTO saveVaccineDTO) {
        VaccineDTO createdVaccine = vaccineService.saveVaccin(saveVaccineDTO);
        return new ResponseEntity<>(createdVaccine, HttpStatus.CREATED);
    }



    /**
     * Obtenir un vaccin par son ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un vaccin par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vaccin trouvé"),
        @ApiResponse(responseCode = "404", description = "Vaccin non trouvé")
    })
    public ResponseEntity<VaccineDTO> obtenirVaccinParId(@PathVariable Long id) {
        VaccineDTO vaccineDTO = vaccineService.getVaccinById(id);
        return ResponseEntity.ok(vaccineDTO);
    }




    /**
     * Mettre à jour un vaccin existant
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un vaccin existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vaccin mis à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Données de vaccin invalides"),
        @ApiResponse(responseCode = "404", description = "Vaccin non trouvé")
    })
    public ResponseEntity<VaccineDTO> mettreAJourVaccin(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVaccineDTO updateVaccineDTO) {
        VaccineDTO updatedVaccine = vaccineService.updateVaccin(id, updateVaccineDTO);
        return ResponseEntity.ok(updatedVaccine);
    }




    /**
     * Supprimer un vaccin
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un vaccin")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vaccin supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Vaccin non trouvé")
    })
    public ResponseEntity<Void> supprimerVaccin(@PathVariable Long id) {
        vaccineService.deleteVaccin(id);
        return ResponseEntity.noContent().build();
    }




    /**
     * Obtenir tous les vaccins avec pagination
     */
    @GetMapping
    @Operation(summary = "Obtenir tous les vaccins avec pagination")
    public ResponseEntity<List<VaccineDTO>> obtenirTousLesVaccins() {
        List<VaccineDTO> vaccines = vaccineService.getAllVaccins();
        return ResponseEntity.ok(vaccines);
    }





}
