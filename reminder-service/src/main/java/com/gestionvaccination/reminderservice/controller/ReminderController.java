package com.gestionvaccination.reminderservice.controller;

import com.gestionvaccination.reminderservice.dto.ReminderDTO;
import com.gestionvaccination.reminderservice.dto.SaveReminderDTO;
import com.gestionvaccination.reminderservice.dto.UpdateReminderDTO;
import com.gestionvaccination.reminderservice.service.ReminderService;
import io.swagger.v3.oas.annotations.Operation;
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
 * Contrôleur REST pour gérer les rappels
 */
@RestController
@RequestMapping("/api/reminders")
@Tag(name = "Gestion des Rappels", description = "APIs de gestion des rappels de vaccination")
@AllArgsConstructor
public class ReminderController {

    private final ReminderService service;

    @PostMapping
    @Operation(summary = "Créer un rappel", description = "Crée un nouveau rappel pour un enfant")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Rappel créé"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Enfant non trouvé")
    })
    public ResponseEntity<ReminderDTO> createReminder(@Valid @RequestBody SaveReminderDTO saveDto) {
        ReminderDTO dto = service.createReminder(saveDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un rappel par ID", description = "Récupère un rappel spécifique")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rappel trouvé"),
        @ApiResponse(responseCode = "404", description = "Rappel non trouvé")
    })
    public ResponseEntity<ReminderDTO> getReminderById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getReminderById(id));
    }

    @GetMapping("/enfant/{enfantId}")
    @Operation(summary = "Liste des rappels par enfant", description = "Récupère tous les rappels pour un enfant")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public ResponseEntity<List<ReminderDTO>> getRemindersByEnfant(@PathVariable Long enfantId) {
        return ResponseEntity.ok(service.getRemindersByEnfant(enfantId));
    }

    @GetMapping
    @Operation(summary = "Liste de tous les rappels", description = "Récupère tous les rappels")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public ResponseEntity<List<ReminderDTO>> getAllReminders() {
        return ResponseEntity.ok(service.getAllReminders());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un rappel", description = "Met à jour un rappel existant")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rappel mis à jour"),
        @ApiResponse(responseCode = "404", description = "Rappel non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<ReminderDTO> updateReminder(@PathVariable Long id, @Valid @RequestBody UpdateReminderDTO updateDto) {
        return ResponseEntity.ok(service.updateReminder(id, updateDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un rappel", description = "Supprime un rappel existant")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Rappel supprimé"),
        @ApiResponse(responseCode = "404", description = "Rappel non trouvé")
    })
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        service.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }
}
