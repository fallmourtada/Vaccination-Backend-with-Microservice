package com.gestionvaccination.userservice.controller;

import com.gestionvaccination.userservice.entites.Utilisateur;
import com.gestionvaccination.userservice.enumeration.UserRole;
import com.gestionvaccination.userservice.repository.UtilisateurRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.gestionvaccination.userservice.dto.*;
import com.gestionvaccination.userservice.services.UtilisateurService;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des utilisateurs
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Gestion des Utilisateurs", description = "APIs pour la gestion des utilisateurs du système de vaccination")
@SecurityRequirement(name = "bearerAuth") // C'est ici que tu appliques la sécurité
@AllArgsConstructor
public class UtilisateurController {
    private final UtilisateurRepository userRepository;

    private final PasswordEncoder passwordEncoder ;
    
    private final UtilisateurService utilisateurService;




    @PostMapping("/parent")
    @Operation(
        summary = "Créer un nouvel utilisateur",
        description = "Crée un nouvel utilisateur dans le système de vaccination"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "409", description = "Utilisateur existe déjà")
    })
    public ResponseEntity<UtilisateurDTO> ajouterParent(
            @RequestBody SaveParentDTO saveParentDTO,@RequestParam Long centreId) {
        
        UtilisateurDTO utilisateur = utilisateurService.saveParent(saveParentDTO,UserRole.PARENT,centreId);
        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }


    @PostMapping("/ICP")
    @Operation(
            summary = "Créer un nouvel utilisateur",
            description = "Crée un nouvel utilisateur dans le système de vaccination"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Utilisateur existe déjà")
    })
    public ResponseEntity<UtilisateurDTO> ajouterIcp(
            @RequestBody SaveInfirmierDTO saveInfirmierDTO,@RequestParam Long districtId) {

        UtilisateurDTO utilisateur = utilisateurService.saveInfirmier(saveInfirmierDTO,UserRole.ICP,districtId);
        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }



    @PostMapping("/Infirmier")
    @Operation(
            summary = "Créer un nouvel utilisateur",
            description = "Crée un nouvel utilisateur dans le système de vaccination"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Utilisateur existe déjà")
    })
    public ResponseEntity<UtilisateurDTO> SaveInfirmier(
            @RequestBody SaveInfirmierDTO saveInfirmierDTO,@RequestParam Long centreId) {

        UtilisateurDTO utilisateur = utilisateurService.saveInfirmier(saveInfirmierDTO,UserRole.INFIRMIER,centreId);
        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }




//    @PostMapping("/SageFemme")
//    @Operation(
//            summary = "Créer un nouvel utilisateur",
//            description = "Crée un nouvel utilisateur dans le système de vaccination"
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
//            @ApiResponse(responseCode = "400", description = "Données invalides"),
//            @ApiResponse(responseCode = "409", description = "Utilisateur existe déjà")
//    })
//    public ResponseEntity<UtilisateurDTO> ajouterSageFemme(
//            @RequestBody SaveUtilisateurDTO saveUtilisateurDTO,@RequestParam Long localityId,@RequestParam Long centreId) {
//
//        UtilisateurDTO utilisateur = utilisateurService.saveUtilisateur(saveUtilisateurDTO,UserRole.SAGE_FEMME,localityId,centreId);
//        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
//    }




    @GetMapping("/{id}")
    @Operation(
        summary = "Obtenir un utilisateur par ID",
        description = "Récupère les détails d'un utilisateur spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UtilisateurDTO> obtenirUtilisateurParId(
            @Parameter(description = "ID de l'utilisateur") @PathVariable Long id) {
        
        UtilisateurDTO utilisateur = utilisateurService.getUserById(id);
        return ResponseEntity.ok(utilisateur);
    }




    @GetMapping("/findUserByEmail/{email}")
    @Operation(
        summary = "Obtenir un utilisateur par email",
        description = "Récupère les détails d'un utilisateur par son email"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UtilisateurDTO> obtenirUtilisateurParEmail(
            @Parameter(description = "Email de l'utilisateur") @PathVariable String email) {
        
        UtilisateurDTO utilisateur = utilisateurService.getUserByEmail(email);
        return ResponseEntity.ok(utilisateur);
    }




    @GetMapping("/telephone/{telephone}")
    @Operation(
        summary = "Obtenir un utilisateur par téléphone",
        description = "Récupère les détails d'un utilisateur par son numéro de téléphone"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UtilisateurDTO> obtenirUtilisateurParTelephone(
            @Parameter(description = "Téléphone de l'utilisateur") @PathVariable String telephone) {
        
        UtilisateurDTO utilisateur = utilisateurService.getUserByTelephone(telephone);
        return ResponseEntity.ok(utilisateur);
    }





    @PutMapping("/{id}")
    @Operation(
        summary = "Mettre à jour un utilisateur",
        description = "Met à jour les informations d'un utilisateur existant"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<UtilisateurDTO> mettreAJourUtilisateur(
            @Parameter(description = "ID de l'utilisateur") @PathVariable Long id,
            @RequestBody UpdateUtilisateurDTO updateUtilisateurDTO) {
        
        UtilisateurDTO utilisateur = utilisateurService.updateUser(id, updateUtilisateurDTO);
        return ResponseEntity.ok(utilisateur);
    }



    @DeleteMapping("/{id}")
    @Operation(
        summary = "Supprimer un utilisateur",
        description = "Supprime un utilisateur du système"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<Void> supprimerUtilisateur(
            @Parameter(description = "ID de l'utilisateur") @PathVariable Long id) {
        
        utilisateurService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping
    @Operation(
        summary = "Obtenir tous les utilisateurs",
        description = "Récupère la liste paginée de tous les utilisateurs"
    )
    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée")
    public ResponseEntity<List<UtilisateurDTO>> obtenirTousLesUtilisateurs() {

        List<UtilisateurDTO> utilisateurs = utilisateurService.getAllUsers();

        return ResponseEntity.ok(utilisateurs);
    }

     // Endpoint pour la validation d'authentification
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateUser(
            @RequestParam String username,
            @RequestParam String password) {
        
        // Pour tester, retournez simplement true
        // Plus tard, vous ajouterez la validation réelle
        return ResponseEntity.ok(true);
    }









   
}
