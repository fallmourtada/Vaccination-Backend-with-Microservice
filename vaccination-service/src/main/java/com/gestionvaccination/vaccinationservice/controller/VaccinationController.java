package com.gestionvaccination.vaccinationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.gestionvaccination.vaccinationservice.service.VaccinationService;
import com.gestionvaccination.vaccinationservice.dto.VaccinationDTO;
import com.gestionvaccination.vaccinationservice.dto.SaveVaccinationDTO;
import com.gestionvaccination.vaccinationservice.dto.UpdateVaccinationDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/vaccinations")
@AllArgsConstructor
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth") // C'est ici que tu appliques la sécurité

public class VaccinationController {
    private final VaccinationService vaccinationService;

    @PostMapping
    public ResponseEntity<VaccinationDTO> creer(@RequestBody SaveVaccinationDTO saveVaccinationDTO,@RequestParam Long vaccinId,@RequestParam Long appointmentId,@RequestParam Long userId,@RequestParam Long enfantId) {
        return ResponseEntity.status(201).body(vaccinationService.saveVaccination(saveVaccinationDTO,vaccinId,appointmentId,userId,enfantId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<VaccinationDTO> lire(@PathVariable Long id) {
        return ResponseEntity.ok(vaccinationService.getVaccinationById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<VaccinationDTO> maj(@PathVariable Long id, @RequestBody UpdateVaccinationDTO dto) {
        return ResponseEntity.ok(vaccinationService.updateVaccination(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        vaccinationService.deleteVaccination(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/enfant/{enfantId}")
    public ResponseEntity<List<VaccinationDTO>> parEnfant(@PathVariable Long enfantId) {
        return ResponseEntity.ok(vaccinationService.getVaccinationByEnfant(enfantId));
    }

    @GetMapping("/vaccine/{vaccineId}")
    public ResponseEntity<List<VaccinationDTO>> parVaccine(@PathVariable Long vaccineId) {
        return ResponseEntity.ok(vaccinationService.getVaccinationByVaccin(vaccineId));
    }




}
