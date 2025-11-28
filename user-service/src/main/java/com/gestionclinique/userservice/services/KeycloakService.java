package com.gestionclinique.userservice.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionclinique.userservice.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
@Setter

public class KeycloakService {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakService.class);
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.secret}")
    private String clientSecret;

    @PostConstruct
    public void initKeycloak() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }



    /**
     * Crée un nouvel utilisateur dans Keycloak
     *
     * @param user représentation de l'utilisateur à créer
     * @return l'identifiant de l'utilisateur créé dans Keycloak
     * @throws RuntimeException en cas d'erreur de création
     */
    public String createUser(UserRepresentation user) {
        Objects.requireNonNull(user, "La représentation de l'utilisateur ne peut pas être nulle");

        try {
            // S'assurer que l'utilisateur est activé
            user.setEnabled(true);
            
            log.debug("Création d'un nouvel utilisateur dans Keycloak: {}", user.getUsername());
            Response response = keycloak.realm(realm).users().create(user);

            if (response.getStatus() == 201) {
                String userId = extractUserIdFromResponse(response);
                log.info("Utilisateur créé avec succès dans Keycloak, ID: {}", userId);
                return userId;
            } else {
                String responseBody = response.readEntity(String.class);
                String errorMsg = String.format(
                        "Échec de création de l'utilisateur dans Keycloak. Statut: %d, Info: %s, Corps: %s",
                        response.getStatus(), response.getStatusInfo(),
                        responseBody != null ? responseBody : "Pas de corps de réponse");
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
        } catch (RuntimeException e) {
            throw e; // Propagation des exceptions déjà encapsulées
        } catch (Exception e) {
            String errorMsg = "Erreur lors de la création de l'utilisateur dans Keycloak: " + e.getMessage();
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }




    /**
     * Extrait l'ID utilisateur de la réponse de création
     *
     * @param response la réponse HTTP de la création d'utilisateur
     * @return l'ID extrait
     * @throws RuntimeException si l'extraction échoue
     */
    private String extractUserIdFromResponse(Response response) {
        if (response.getLocation() == null) {
            throw new RuntimeException("URL de localisation non trouvée dans la réponse");
        }

        String path = response.getLocation().getPath();
        if (path == null) {
            throw new RuntimeException("Format de chemin invalide dans la réponse");
        }

        // Solution plus robuste : récupérer le dernier élément du chemin
        String[] pathSegments = path.split("/");
        return pathSegments[pathSegments.length - 1]; // Prend toujours le dernier segment
    }




    /**
     * Récupère un utilisateur Keycloak par son ID et enrichit sa représentation
     * avec ses rôles
     *
     * @param userId ID de l'utilisateur dans Keycloak
     * @return Une représentation de l'utilisateur avec ses rôles correctement
     *         renseignés
     * @throws RuntimeException en cas d'erreur lors de la récupération
     */
    public UserRepresentation getUser(String userId) {
        try {
            validateUserId(userId);
            log.debug("Récupération de l'utilisateur Keycloak avec ID: {}", userId);

            // Récupérer l'utilisateur et ses rôles
            UserResource userResource = getUserResource(userId);
            UserRepresentation userRepresentation = getUserRepresentation(userResource);
            enrichUserWithRoles(userId, userRepresentation, userResource);

            return userRepresentation;
        } catch (IllegalArgumentException e) {
            log.error("Erreur de validation: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'utilisateur {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la récupération de l'utilisateur Keycloak", e);
        }
    }





    /**
     * Obtient la représentation d'un utilisateur à partir d'une ressource
     * utilisateur
     *
     * @param userResource La ressource utilisateur Keycloak
     * @return La représentation de l'utilisateur
     * @throws RuntimeException si la représentation est null
     */
    private UserRepresentation getUserRepresentation(UserResource userResource) {
        if (userResource == null) {
            throw new RuntimeException("La ressource utilisateur est null");
        }

        UserRepresentation representation = userResource.toRepresentation();
        if (representation == null) {
            throw new RuntimeException("La représentation de l'utilisateur est null");
        }

        return representation;
    }




    /**
     * Enrichit une représentation d'utilisateur avec ses rôles Keycloak
     *
     * @param userId             L'ID de l'utilisateur
     * @param userRepresentation La représentation à enrichir
     * @param userResource       La ressource utilisateur pour accéder aux rôles
     */
    private void enrichUserWithRoles(String userId, UserRepresentation userRepresentation, UserResource userResource) {
        try {
            // Récupérer les rôles du royaume directement depuis l'API des rôles
            List<RoleRepresentation> roleRepresentations = userResource.roles().realmLevel().listAll();
            List<String> roleNames = roleRepresentations.stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toList());

            // Enrichir la représentation avec les rôles
            userRepresentation.setRealmRoles(roleNames);

            log.debug("Utilisateur {} enrichi avec {} rôles: {}", userId, roleNames.size(),
                    roleNames.isEmpty() ? "Aucun" : String.join(", ", roleNames));
        } catch (Exception e) {
            log.warn("Erreur lors de l'enrichissement des rôles pour l'utilisateur {}: {}", userId, e.getMessage());
            // Initialiser la liste de rôles vide plutôt que de laisser à null
            userRepresentation.setRealmRoles(Collections.emptyList());
        }
    }




    /**
     * Récupère une ressource utilisateur Keycloak par son ID
     *
     * @param userId ID de l'utilisateur
     * @return la ressource utilisateur correspondante
     * @throws IllegalArgumentException si l'ID est invalide
     */
    public UserResource getUserResource(String userId) {
        validateUserId(userId);
        return keycloak.realm(realm).users().get(userId);
    }




    /**
     * Met à jour un utilisateur dans Keycloak
     *
     * @param userId ID de l'utilisateur à mettre à jour
     * @param user   Nouvelle représentation de l'utilisateur
     * @throws RuntimeException en cas d'erreur pendant la mise à jour
     */
    public void updateUser(String userId, UserRepresentation user) {
        validateUserId(userId);
        Objects.requireNonNull(user, "La représentation de l'utilisateur ne peut pas être nulle");

        try {
            log.debug("Mise à jour de l'utilisateur Keycloak {}", userId);
            keycloak.realm(realm).users().get(userId).update(user);
            log.info("Utilisateur {} mis à jour avec succès dans Keycloak", userId);
        } catch (Exception e) {
            String errorMsg = "Erreur lors de la mise à jour de l'utilisateur dans Keycloak: " + e.getMessage();
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }





    public void deleteUser(String userId) {
        validateUserId(userId);

        try {
            keycloak.realm(realm)
                    .users()
                    .get(userId)
                    .remove();

            log.info("Utilisateur {} supprimé avec succès de Keycloak", userId);
        } catch (Exception e) {
            if (e.getMessage().contains("404") || e.getMessage().contains("Not Found")) {
                log.warn("Utilisateur non trouvé dans Keycloak: {}", userId);
            } else {
                String errorMsg = "Erreur lors de la suppression de l'utilisateur dans Keycloak: " + e.getMessage();
                log.error(errorMsg, e);
                throw new RuntimeException(errorMsg, e);
            }
        }

    }




    /**
     * Supprime un utilisateur de Keycloak
     *
     * @param userId ID de l'utilisateur à supprimer
     * @throws RuntimeException en cas d'erreur pendant la suppression
     */
    /**
    public void deleteUser(String userId) {
        validateUserId(userId);

        try {
            log.debug("Suppression de l'utilisateur Keycloak {}", userId);
            Response response = keycloak.realm(realm).users().delete(userId);

            if (response.getStatus() == 204) {
                log.info("Utilisateur {} supprimé avec succès de Keycloak", userId);
            } else {
                String errorMsg = "Échec de la suppression de l'utilisateur dans Keycloak: " + response.getStatusInfo();
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
        } catch (RuntimeException e) {
            throw e; // Propagation des exceptions déjà encapsulées
        } catch (Exception e) {
            String errorMsg = "Erreur lors de la suppression de l'utilisateur dans Keycloak: " + e.getMessage();
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }




    /**
     * Récupère la représentation d'un rôle à partir de son nom
     *
     * @param name Le nom du rôle à rechercher
     * @return La représentation du rôle
     * @throws RuntimeException si le rôle n'existe pas ou en cas d'erreur
     */
    public RoleRepresentation getRoleRepresentation(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Le nom du rôle ne peut pas être null ou vide");
            }

            log.debug("Récupération de la représentation du rôle '{}'", name);
            RoleRepresentation role = keycloak.realm(realm).roles().get(name).toRepresentation();

            if (role == null) {
                throw new RuntimeException("Le rôle '" + name + "' n'existe pas dans Keycloak");
            }

            return role;
        } catch (IllegalArgumentException e) {
            log.error("Erreur de validation: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            String errorMsg = "Erreur lors de la récupération du rôle '" + name + "': " + e.getMessage();
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }




    /**
     * Assigner explicitement un rôle du royaume à un utilisateur
     *
     * @param userId   ID de l'utilisateur
     * @param roleName Nom du rôle à assigner
     * @throws RuntimeException en cas d'erreur pendant l'assignation
     */
    public void assignRealmRoleToUser(String userId, String roleName) {
        modifyUserRole(userId, roleName, true);
    }





    /**
     * Supprimer explicitement un rôle du royaume d'un utilisateur
     *
     * @param userId   ID de l'utilisateur
     * @param roleName Nom du rôle à supprimer
     * @throws RuntimeException en cas d'erreur pendant la suppression
     */
    public void removeRealmRoleFromUser(String userId, String roleName) {
        modifyUserRole(userId, roleName, false);
    }



    /**
     * Modifie (ajout ou suppression) un rôle pour un utilisateur
     *
     * @param userId   ID de l'utilisateur
     * @param roleName Nom du rôle
     * @param isAdd    true pour ajouter, false pour supprimer
     * @throws RuntimeException en cas d'erreur pendant l'opération
     */
    private void modifyUserRole(String userId, String roleName, boolean isAdd) {
        String operation = isAdd ? "d'ajout" : "de suppression";
        String actionVerb = isAdd ? "ajouter" : "supprimer";

        try {
            // Valider les paramètres
            validateUserId(userId);
            if (roleName == null || roleName.trim().isEmpty()) {
                throw new IllegalArgumentException("Le nom du rôle ne peut pas être null ou vide");
            }

            // Récupérer la représentation du rôle
            RoleRepresentation role = getRoleRepresentation(roleName);
            log.debug("Tentative {} du rôle '{}' pour l'utilisateur {}", operation, roleName, userId);

            // Récupérer la ressource utilisateur
            UserResource userResource = getUserResource(userId);

            // Vérifier d'abord si l'opération est nécessaire
            boolean hasRole = checkIfUserHasRole(userResource, roleName);

            if (isAdd && hasRole) {
                log.info("L'utilisateur {} possède déjà le rôle '{}', aucune action nécessaire", userId, roleName);
                return;
            } else if (!isAdd && !hasRole) {
                log.info("L'utilisateur {} ne possède pas le rôle '{}', aucune action nécessaire", userId, roleName);
                return;
            }

            // Modifier le rôle
            if (isAdd) {
                userResource.roles().realmLevel().add(Collections.singletonList(role));
            } else {
                userResource.roles().realmLevel().remove(Collections.singletonList(role));
            }

            log.info("Rôle '{}' {} avec succès pour l'utilisateur {}",
                    roleName, isAdd ? "ajouté" : "supprimé", userId);

            // Journaliser les rôles actuels
            logCurrentRoles(userId);
        } catch (IllegalArgumentException e) {
            log.error("Erreur de validation: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            String errorMsg = String.format("Erreur lors de la tentative de %s le rôle '%s' à l'utilisateur %s: %s",
                    actionVerb, roleName, userId, e.getMessage());
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }




    /**
     * Vérifie si un utilisateur a un rôle spécifique
     *
     * @param userResource La ressource utilisateur
     * @param roleName     Le nom du rôle à vérifier
     * @return true si l'utilisateur possède le rôle, false sinon
     */
    private boolean checkIfUserHasRole(UserResource userResource, String roleName) {
        List<RoleRepresentation> roles = userResource.roles().realmLevel().listAll();
        return roles.stream()
                .map(RoleRepresentation::getName)
                .anyMatch(name -> name.equals(roleName));
    }

    /**
     * Journalise les rôles actuels d'un utilisateur
     *
     * @param userId ID de l'utilisateur
     */
    private void logCurrentRoles(String userId) {
        try {
            UserResource userResource = getUserResource(userId);
            List<RoleRepresentation> roles = userResource.roles().realmLevel().listAll();

            if (roles.isEmpty()) {
                log.debug("L'utilisateur {} n'a aucun rôle", userId);
                return;
            }

            String roleList = roles.stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.joining(", "));

            log.debug("Rôles actuels de l'utilisateur {} : {}", userId, roleList);
        } catch (Exception e) {
            log.warn("Impossible de récupérer les rôles actuels de l'utilisateur {}: {}", userId, e.getMessage());
        }
    }




    /**
     * Valide qu'un ID utilisateur n'est pas nul ou vide
     * Lève une exception IllegalArgumentException si l'ID est invalide
     *
     * @param userId ID de l'utilisateur à valider
     * @throws IllegalArgumentException si l'ID est null ou vide
     */
    private void validateUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            log.error("ID utilisateur invalide: {}", userId);
            throw new IllegalArgumentException("L'ID utilisateur ne peut pas être null ou vide");
        }
    }

    /**
     * Recherche des utilisateurs par un attribut spécifique
     *
     * @param attributeName  Nom de l'attribut
     * @param attributeValue Valeur de l'attribut
     * @return Une liste de représentations d'utilisateurs correspondants
     */
    public List<UserRepresentation> searchUsersByAttribute(String attributeName, String attributeValue) {
        Objects.requireNonNull(attributeName, "Le nom de l'attribut ne peut pas être nul");
        Objects.requireNonNull(attributeValue, "La valeur de l'attribut ne peut pas être nulle");

        try {
            log.debug("Recherche d'utilisateurs avec {}={}", attributeName, attributeValue);
            List<UserRepresentation> users = keycloak.realm(realm)
                    .users()
                    .searchByAttributes(attributeName + ":" + attributeValue);

            log.debug("{} utilisateur(s) trouvé(s) avec {}={}",
                    users.size(), attributeName, attributeValue);

            // Enrichir chaque utilisateur avec ses rôles
            users.forEach(user -> {
                try {
                    UserResource userResource = getUserResource(user.getId());
                    enrichUserWithRoles(user.getId(), user, userResource);
                } catch (Exception e) {
                    log.warn("Impossible d'enrichir l'utilisateur {} avec ses rôles: {}",
                            user.getId(), e.getMessage());
                }
            });

            return users;
        } catch (Exception e) {
            String errorMsg = String.format("Erreur lors de la recherche d'utilisateurs par %s=%s: %s",
                    attributeName, attributeValue, e.getMessage());
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    /**
     * Vérifie si un utilisateur existe dans Keycloak
     *
     * @param userId ID de l'utilisateur à vérifier
     * @return true si l'utilisateur existe, false sinon
     */
    public boolean userExists(String userId) {
        try {
            validateUserId(userId);
            // Tentative de récupération - si aucune exception n'est levée, l'utilisateur
            // existe
            getUserResource(userId).toRepresentation();
            return true;
        } catch (IllegalArgumentException e) {
            // ID invalide
            log.debug("Vérification d'existence avec un ID invalide: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            // Toute autre exception indique généralement que l'utilisateur n'existe pas
            log.debug("L'utilisateur avec ID {} n'existe pas: {}", userId, e.getMessage());
            return false;
        }
    }

    /**
     * Récupère tous les rôles disponibles dans le realm
     *
     * @return Liste des représentations de rôles
     */
    public List<RoleRepresentation> getAllRealmRoles() {
        try {
            log.debug("Récupération de tous les rôles du realm {}", realm);
            List<RoleRepresentation> roles = keycloak.realm(realm).roles().list();

            log.debug("{} rôles trouvés dans le realm", roles.size());
            return roles;
        } catch (Exception e) {
            String errorMsg = "Erreur lors de la récupération des rôles du realm: " + e.getMessage();
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    /**
     * Réinitialise le mot de passe d'un utilisateur
     *
     * @param userId      ID de l'utilisateur
     * @param newPassword Nouveau mot de passe
     * @param temporary   true si le mot de passe est temporaire (l'utilisateur
     *                    devra le changer à la prochaine connexion)
     */
    public void resetUserPassword(String userId, String newPassword, boolean temporary) {
        validateUserId(userId);
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être null ou vide");
        }

        try {
            log.debug("Réinitialisation du mot de passe pour l'utilisateur {}", userId);

            // Créer la représentation des informations d'identification
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(newPassword);
            credential.setTemporary(temporary);

            // Mettre à jour le mot de passe
            UserResource userResource = getUserResource(userId);
            userResource.resetPassword(credential);

            log.info("Mot de passe réinitialisé avec succès pour l'utilisateur {}", userId);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            String errorMsg = "Erreur lors de la réinitialisation du mot de passe: " + e.getMessage();
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }



    /**
     * Chercher un User par son  email
     *
     * @param email de l'utilisateur

     */

    public UserRepresentation findUserByEmail(String email) {
        List<UserRepresentation> users = keycloak.realm(realm)
                .users()
                .search(email, true);

        if (users.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        return users.get(0); // premier résultat
    }


    /**
     * Récupère tous les utilisateurs Keycloak qui possèdent un rôle de realm spécifique.
     * Cette méthode utilise la pagination pour s'assurer que tous les utilisateurs sont récupérés.
     * Elle filtre les utilisateurs côté application en vérifiant leurs rôles.
     *
     * @param roleName Le nom du rôle (ex: "AGENT", "ADHERENT").
     * @return Une liste de UserRepresentation de tous les utilisateurs ayant ce rôle.
     * @throws RuntimeException si une erreur de communication avec Keycloak survient.
     */
    public List<UserRepresentation> getUsersByRealmRole(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du rôle ne peut pas être null ou vide.");
        }

        List<UserRepresentation> usersWithSpecificRole = new ArrayList<>();
        int first = 0;
        // Définissez une taille de page raisonnable.
        // Keycloak retourne par défaut un maximum de 100 utilisateurs si non spécifié.
        // Utilisons 100 pour être sûr. Ajustez si vous savez que votre Keycloak supporte plus.
        int max = 100;

        try {
            log.debug("Récupération de tous les utilisateurs du realm et filtrage par rôle '{}'.", roleName);

            while (true) {
                // Récupérer une page d'utilisateurs du realm
                // L'API list(first, max) ne fournit pas toujours les rôles de realm par défaut.
                // Nous allons donc ensuite appeler getUser() pour chaque utilisateur.
                List<UserRepresentation> currentPageUsers = keycloak.realm(realm).users().list(first, max);

                if (currentPageUsers.isEmpty()) {
                    break; // Plus d'utilisateurs à récupérer
                }

                // Pour chaque utilisateur dans la page, récupérer sa représentation complète
                // qui inclut les rôles (grâce à votre méthode getUser existante)
                for (UserRepresentation briefUser : currentPageUsers) {
                    try {
                        UserRepresentation fullUser = getUser(briefUser.getId()); // Votre méthode getUser enrichit l'utilisateur avec ses rôles

                        // Vérifier si l'utilisateur complet a le rôle spécifié
                        if (fullUser != null && fullUser.getRealmRoles() != null && fullUser.getRealmRoles().contains(roleName)) {
                            usersWithSpecificRole.add(fullUser);
                        }
                    } catch (Exception e) {
                        log.warn("Impossible d'enrichir l'utilisateur Keycloak ID {} avec ses rôles: {}. Ignoré.", briefUser.getId(), e.getMessage());
                        // Continuer même si un utilisateur particulier ne peut pas être enrichi
                    }
                }

                first += currentPageUsers.size(); // Avancer l'index de départ pour la prochaine page

                // Si la page retournée est plus petite que `max`, c'était la dernière page.
                if (currentPageUsers.size() < max) {
                    break;
                }
            }

            log.info("Récupération et filtrage terminés. Trouvé {} utilisateurs avec le rôle '{}'.", usersWithSpecificRole.size(), roleName);
            return usersWithSpecificRole;

        } catch (IllegalArgumentException e) {
            log.error("Erreur de validation lors de la récupération des utilisateurs par rôle: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            String errorMsg = String.format("Erreur lors de la récupération paginée des utilisateurs pour le rôle '%s': %s", roleName, e.getMessage());
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    public void updateUserStatus(String keycloakId, boolean enabled) {
        try {
            UserResource userResource = keycloak.realm(realm).users().get(keycloakId);
            UserRepresentation user = userResource.toRepresentation();
            user.setEnabled(enabled);
            userResource.update(user);
            log.info("Utilisateur {} {} dans Keycloak", keycloakId, enabled ? "activé" : "désactivé");
        } catch (NotFoundException e) {
            log.error("Utilisateur non trouvé dans Keycloak: {}", keycloakId);
            throw new ResourceNotFoundException("Utilisateur Keycloak non trouvé.");
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour du statut de l'utilisateur: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'activation/désactivation de l'utilisateur.");
        }
    }


    /**
    public String getAdminAccessToken() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(realm)
                .clientId(clientId) // ou ton client confidentiel
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();

        return keycloak.tokenManager().getAccessToken().getToken();
    }


    public String getAdminAccessToken() {
        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token");

        Form form = new Form();
        form.param("grant_type", "client_credentials");
        form.param("client_id", clientId);
        form.param("client_secret", clientSecret);

        Response response = target
                .request()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(Entity.form(form));

        if (response.getStatus() != 200) {
            String error = response.readEntity(String.class);
            throw new RuntimeException("Erreur lors de l'obtention du token admin : " + error);
        }

        Map<String, Object> jsonResponse = response.readEntity(Map.class);
        String accessToken = (String) jsonResponse.get("access_token");

        if (accessToken == null) {
            throw new RuntimeException("Token admin manquant dans la réponse Keycloak.");
        }

        return accessToken;
    }




    public String getTokenForUser(String email) {
        // Récupérer l'utilisateur Keycloak
        UserRepresentation user = findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé dans Keycloak");
        }

        String userId = user.getId();

        // Appeler l’API impersonation avec le token admin
        String adminToken = getAdminAccessToken(); // Voir méthode ci-dessous

        String impersonationUrl = String.format("%s/admin/realms/%s/users/%s/impersonation",
                authServerUrl, realm, userId);

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(impersonationUrl);

        Response response = target
                .request()
                .header("Authorization", "Bearer " + adminToken)
                .post(Entity.json(null));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Erreur d’impersonation : " + response.getStatus());
        }

        Map<String, Object> responseMap = response.readEntity(Map.class);
        return (String) responseMap.get("access_token");
    }

     */


    /**
     * Échange un userId Keycloak contre un token d'accès
     * @param userId L'ID Keycloak de l'utilisateur (pas le username)
     * @return Le token JWT généré
     * @throws TokenExchangeException Si l'échange échoue
     */
    public String exchangeToken(String userId) throws TokenExchangeException {
        try {
            logger.info("Début de l'échange de token pour l'utilisateur: {}", userId);

            // 1. Obtenir le token admin
            String adminToken = getAdminToken();
            if (adminToken == null || adminToken.isEmpty()) {
                throw new TokenExchangeException("Token admin invalide ou vide");
            }
            logger.debug("Token admin obtenu avec succès");

            // 2. Construire la requête
            String url = String.format("%s/realms/%s/protocol/openid-connect/token",
                    authServerUrl, realm);
            logger.debug("URL de token exchange: {}", url);

            String formData = String.format(
                    "grant_type=urn:ietf:params:oauth:token-exchange&" +
                            "client_id=%s&" +
                            "client_secret=%s&" +
                            "subject_token=%s&" +
                            "requested_subject=%s",
                    clientId, clientSecret, adminToken, userId
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(formData))
                    .timeout(Duration.ofSeconds(15))
                    .build();

            // 3. Envoyer la requête
            logger.debug("Envoi de la requête de token exchange...");
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.debug("Réponse reçue - Status: {}", response.statusCode());

            // 4. Traiter la réponse
            if (response.statusCode() == 200) {
                JsonNode json = objectMapper.readTree(response.body());
                String accessToken = json.get("access_token").asText();

                if (accessToken == null || accessToken.isEmpty()) {
                    throw new TokenExchangeException("Token manquant dans la réponse");
                }

                logger.info("Token exchange réussi pour l'utilisateur {}", userId);
                return accessToken;
            } else {
                String errorDetails = extractErrorDetails(response.body());
                logger.error("Erreur lors de l'échange de token: {} - {}", response.statusCode(), errorDetails);
                throw new TokenExchangeException(
                        String.format("Erreur Keycloak [%d]: %s", response.statusCode(), errorDetails)
                );
            }
        } catch (TokenExchangeException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Échec technique de l'échange de token pour l'utilisateur " + userId, e);
            throw new TokenExchangeException("Échec technique: " + e.getMessage(), e);
        }
    }

    private String getAdminToken() throws TokenExchangeException {
        try {
            logger.debug("Tentative d'obtention du token admin...");

            // Utilisez le realm 'chift-realm' au lieu de 'master'
            String url = String.format("%s/realms/%s/protocol/openid-connect/token",
                    authServerUrl, realm);

            String formData = String.format(
                    "grant_type=client_credentials&" +
                            "client_id=%s&" +
                            "client_secret=%s",
                    clientId, clientSecret
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(formData))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode json = objectMapper.readTree(response.body());
                String token = json.get("access_token").asText();
                logger.debug("Token admin obtenu avec succès");
                return token;
            } else {
                String errorMsg = "Impossible d'obtenir le token admin: " + response.body();
                logger.error(errorMsg);
                throw new TokenExchangeException(errorMsg);
            }
        } catch (Exception e) {
            logger.error("Échec de l'obtention du token admin", e);
            throw new TokenExchangeException("Échec de l'obtention du token admin: " + e.getMessage(), e);
        }
    }

    private String extractErrorDetails(String responseBody) {
        try {
            JsonNode json = objectMapper.readTree(responseBody);
            String error = json.has("error") ? json.get("error").asText() : "Unknown error";
            String description = json.has("error_description") ? json.get("error_description").asText() : "No description";
            return error + ": " + description;
        } catch (Exception e) {
            logger.warn("Impossible d'extraire les détails de l'erreur", e);
            return responseBody;
        }
    }

    public static class TokenExchangeException extends Exception {
        public TokenExchangeException(String message) { super(message); }
        public TokenExchangeException(String message, Throwable cause) { super(message, cause); }
    }
}















