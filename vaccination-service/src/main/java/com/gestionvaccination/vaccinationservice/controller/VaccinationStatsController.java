package com.gestionvaccination.vaccinationservice.controller;

import com.gestionvaccination.vaccinationservice.service.impl.VaccinationStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/vaccinations/stats")
@Tag(name = "Statistiques de vaccination", description = "Endpoints pour le comptage des vaccinations par statut et par zone géographique (centre, district)")
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth") // C'est ici que tu appliques la sécurité
@AllArgsConstructor

public class VaccinationStatsController {

    private final VaccinationStatsService vaccinationStatsService;

    @GetMapping("/by-centre/{centreId}")
    @Operation(
            summary = "Obtenir les statistiques de vaccination par centre",
            description = "Fournit le nombre d'enfants vaccinés et non vaccinés (garçons et filles) pour un centre de santé spécifique."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Statistiques par centre récupérées avec succès",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "object",
                            properties = {
//                                    @io.swagger.v3.oas.annotations.media.Schema(name = "totalEnfants", type = "integer", example = "200"),
//                                    @io.swagger.v3.oas.annotations.media.Schema(name = "enfantsVaccines", type = "integer", example = "120"),
//                                    @io.swagger.v3.oas.annotations.media.Schema(name = "garconsVaccines", type = "integer", example = "65"),
//                                    @io.swagger.v3.oas.annotations.media.Schema(name = "fillesVaccinees", type = "integer", example = "55"),
//                                    @io.swagger.v3.oas.annotations.media.Schema(name = "garconsNonVaccines", type = "integer", example = "40"),
//                                    @io.swagger.v3.oas.annotations.media.Schema(name = "fillesNonVaccinees", type = "integer", example = "40")
                            }))
    )
    @Parameter(name = "centreId", description = "L'identifiant unique du centre de santé.", required = true, example = "1")
    public Map<String, Long> getStatsByCentre(@PathVariable Long centreId) {
        return vaccinationStatsService.getStatsByCentre(centreId);
    }


    @GetMapping("/total")
    @Operation(
            summary = "Obtenir les statistiques de vaccination globales",
            description = "Retourne le nombre total d'enfants vaccinés, y compris le nombre de garçons et de filles."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Statistiques récupérées avec succès",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "object",
                            properties = {
//                                    @io.swagger.v3.oas.annotations.media.Schema(name = "nombreTotalEnfantsVaccines", type = "integer", example = "150"),
//                                    @io.swagger.v3.oas.annotations.media.Schema(name = "nombreGarconsVaccines", type = "integer", example = "75"),
//                                    @io.swagger.v3.oas.annotations.media.Schema(name = "nombreFillesVaccinees", type = "integer", example = "75")
                            }))
    )
    public Map<String, Long> getComptageGlobal() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("nombreTotalEnfantsVaccines", vaccinationStatsService.getNombreTotalEnfantsVaccines());
        stats.put("nombreGarconsVaccines", vaccinationStatsService.getNombreGarconsVaccines());
        stats.put("nombreFillesVaccinees", vaccinationStatsService.getNombreFillesVaccinees());
        return stats;
    }



    @GetMapping("/by-district/{districtId}")
    @Operation(
            summary = "Obtenir les statistiques de vaccination par district",
            description = "Fournit les statistiques de vaccination consolidées pour un district, avec une ventilation par centre de santé."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Statistiques par district récupérées avec succès",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Map.class))
    )
    @Parameter(name = "districtId", description = "L'identifiant unique du district.", required = true, example = "1")
    public Map<String, Object> getStatsByDistrict(@PathVariable Long districtId) {
        return vaccinationStatsService.getStatsByDistrict(districtId);
    }


}