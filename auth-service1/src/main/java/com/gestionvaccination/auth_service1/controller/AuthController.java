package com.gestionvaccination.auth_service1.controller;

import com.gestionvaccination.auth_service1.config.JwtUtils;
import com.gestionvaccination.auth_service1.dto.*;
import com.gestionvaccination.auth_service1.mapper.UserMapper;
import com.gestionvaccination.auth_service1.repository.UserRepository;
import com.gestionvaccination.auth_service1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth/v1")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userDtoMapper;
    private final UserService userService;

    /**
     * Endpoint d'enregistrement (register)
     * Vérifie si le username existe déjà, sinon enregistre l'utilisateur
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated UserDto userDto) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Utilisateur existe déjà");
        }

        // Hasher le mot de passe avant de sauvegarder
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(userDtoMapper.toEntity(userDto));

        // Sauvegarder en BDD et retourner l'utilisateur créé
        return ResponseEntity.ok().body(Map.of("message", "Utilisateur enregistré avec succès"));
    }

    /**
     * Endpoint de connexion (login)
     * Authentifie l'utilisateur et génère un JWT en cas de succès
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest loginRequest) {
        try {
            // On tente d'authentifier avec le username et password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Vérifier si l'authentification est réussie
            if (authentication.isAuthenticated()) {
                // Utiliser l'objet principal de l'authentification qui est un UserDetails
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                // Construire la réponse avec le token JWT en passant l'objet UserDetails
                AuthResponse response = AuthResponse.builder()
                        .token(jwtUtils.generateToken(userDetails))
                        .type("Bearer")
                        .build();

                return ResponseEntity.ok(response);
            }

            // Si authentification échoue
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Nom d'utilisateur ou mot de passe incorrect"));

        } catch (AuthenticationException e) {
            // Si une exception survient (mot de passe incorrect, user introuvable, etc.)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Nom d'utilisateur ou mot de passe incorrect"));
        }
    }

    /**
     * Endpoint pour changer le mot de passe
     */
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Validated ChangePasswordRequest request) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.changePassword(username, request);
            return ResponseEntity.ok().body(Map.of("message", "Mot de passe changé avec succès"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erreur lors du changement de mot de passe"));
        }
    }

    /**
     * Endpoint pour vérifier le statut du système (premier démarrage)
     */
    @GetMapping("/system-status")
    public ResponseEntity<?> getSystemStatus() {
        boolean isFirstStartup = userService.isFirstStartup();
        return ResponseEntity.ok().body(Map.of(
                "firstStartup", isFirstStartup,
                "hasAdmin", !isFirstStartup,
                "message", isFirstStartup ?
                        "Premier démarrage - un admin par défaut sera créé" :
                        "Système initialisé avec succès"
        ));
    }

    @PostMapping("/users")
    public UserDto saveUser(@RequestBody AuthRequestDTO authRequestDTO){
        return userService.saveUser(authRequestDTO);
    }
}