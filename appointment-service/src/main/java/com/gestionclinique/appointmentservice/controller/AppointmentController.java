package com.gestionclinique.appointmentservice.controller;

import com.gestionclinique.appointmentservice.dto.AppointmentDTO;
import com.gestionclinique.appointmentservice.dto.SaveAppointmentDTO;
import com.gestionclinique.appointmentservice.dto.UpdateAppointmentDTO;
import com.gestionclinique.appointmentservice.dto.UpdateStatutAppointmentDTO;
import com.gestionclinique.appointmentservice.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des rendez-vous
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/appointments")
@Tag(name = "Gestion des Rendez-vous", description = "APIs pour la gestion des rendez-vous de vaccination")
@SecurityRequirement(name = "bearerAuth") // C'est ici que tu appliques la sécurité
@AllArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;



    @PostMapping
    @Operation(summary = "Créer un rendez-vous", description = "Crée un nouveau rendez-vous pour un enfant")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rendez-vous créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Enfant non trouvé")
    })
    public ResponseEntity<AppointmentDTO> createAppointment( @RequestBody SaveAppointmentDTO saveAppointmentDTO,@RequestParam Long patientId,@RequestParam Long infirmierId) {
        AppointmentDTO dto = appointmentService.createAppointment(saveAppointmentDTO,patientId,infirmierId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un rendez-vous par ID", description = "Récupère un rendez-vous spécifique")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rendez-vous trouvé"),
        @ApiResponse(responseCode = "404", description = "Rendez-vous non trouvé")
    })
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }




//
//    @GetMapping("/user/{userId}")
//    @Operation(summary = "Liste des rendez-vous d'un infirmier", description = "Récupère tous les rendez-vous pour un infirmier")
//    @ApiResponse(responseCode = "200", description = "Liste retournée")
//    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByInfirmier(@PathVariable Long userId) {
//        return ResponseEntity.ok(appointmentService.getAppointmentsByUserId(userId));
//    }



    @GetMapping
    @Operation(summary = "Liste de tous les rendez-vous", description = "Récupère tous les rendez-vous")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }



    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un rendez-vous", description = "Met à jour un rendez-vous existant")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rendez-vous mis à jour"),
        @ApiResponse(responseCode = "404", description = "Rendez-vous non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id,@RequestBody UpdateAppointmentDTO updateDto) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, updateDto));
    }




    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un rendez-vous", description = "Supprime un rendez-vous existant")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Rendez-vous supprimé"),
        @ApiResponse(responseCode = "404", description = "Rendez-vous non trouvé")
    })
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping ("/{appointmentId}/status")
    public ResponseEntity<Void> updateStatutAppointment(
            @PathVariable Long appointmentId,
            @RequestBody UpdateStatutAppointmentDTO updateStatutAppointmentDTO
            ) {
        appointmentService.updateStatut(appointmentId, updateStatutAppointmentDTO);
        return ResponseEntity.noContent().build();
    }






}
