package com.gestionvaccination.reportingservice.controller;

import com.gestionvaccination.reportingservice.dto.ReportEntryDTO;
import com.gestionvaccination.reportingservice.enumeration.ReportType;
import com.gestionvaccination.reportingservice.service.ReportingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/reports")
@Tag(name = "Reporting Service", description = "APIs pour générer des rapports")
@AllArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping
    @Operation(summary = "Générer un rapport", description = "Génère un rapport selon le type spécifié")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rapport généré"),
        @ApiResponse(responseCode = "400", description = "Type de rapport invalide")
    })
    public List<ReportEntryDTO> generateReport(@RequestParam ReportType type) {
        return reportingService.generateReport(type);
    }
}
