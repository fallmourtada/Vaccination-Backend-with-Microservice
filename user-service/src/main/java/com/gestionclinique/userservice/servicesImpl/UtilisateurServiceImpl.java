package com.gestionclinique.userservice.servicesImpl;


import com.gestionclinique.userservice.dto.SaveUtilisateurDTO;
import com.gestionclinique.userservice.dto.UpdateUtilisateurDTO;
import com.gestionclinique.userservice.dto.UtilisateurDTO;
import com.gestionclinique.userservice.entites.Utilisateur;
import com.gestionclinique.userservice.enumeration.UserRole;
import com.gestionclinique.userservice.exception.ResourceNotFoundException;
import com.gestionclinique.userservice.mapper.UtilisateurMapper;
import com.gestionclinique.userservice.repository.UtilisateurRepository;
import com.gestionclinique.userservice.services.KeycloakService;
import com.gestionclinique.userservice.services.UtilisateurService;
import com.google.zxing.WriterException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    private final UtilisateurMapper utilisateurMapper;

    private final KeycloakService keycloakService;



    @Override
    public UtilisateurDTO saveUser(SaveUtilisateurDTO saveUtilisateurDTO, UserRole userRole) {

        Utilisateur user = utilisateurMapper.fromSaveUtilisateurDTO(saveUtilisateurDTO);

        // Création de la représentation Keycloak (sans ajouter de rôles à ce stade)
        // Important : utiliser la version sans userRole pour la création initiale
        UserRepresentation userRepresentation = getNewUserRepresentation(saveUtilisateurDTO);
        // Création de l'utilisateur dans Keycloak
        String keycloakId = keycloakService.createUser(userRepresentation);
        user.setKeycloakId(keycloakId);

        // Assignation explicite du rôle dans Keycloak APRÈS création
        if (keycloakId != null && userRole != null) {
            log.info("Ajout du rôle {} à l'utilisateur Keycloak {}", userRole.name(), keycloakId);
            try {
                // Utilisation de la méthode addRealmRoleToKeycloakUser pour assigner le rôle
                // directement par l'API
                keycloakService.assignRealmRoleToUser(keycloakId, userRole.name());
                log.info("Rôle {} ajouté avec succès à l'utilisateur {}", userRole.name(), keycloakId);

                // Récupérer l'utilisateur avec ses rôles mis à jour
                UserRepresentation keycloakUser = keycloakService.getUser(keycloakId);
                if (keycloakUser != null) {
                    log.debug("Rôles récupérés de Keycloak: {}", keycloakUser.getRealmRoles());
                }
            } catch (Exception e) {
                log.error("Erreur lors de l'assignation du rôle à l'utilisateur: {}", e.getMessage(), e);
                // Continuer malgré l'erreur, car l'utilisateur est déjà créé
            }
        }

        user.setUserRole(userRole);
         Utilisateur savedUser = utilisateurRepository.save(user);

        return utilisateurMapper.fromEntity(savedUser);
    }


    @Override
    public UtilisateurDTO saveInfirmier(SaveUtilisateurDTO saveUtilisateurDTO, UserRole userRole) {
        return saveUser(saveUtilisateurDTO,UserRole.INFIRMIER);
    }

    @Override
    public UtilisateurDTO saveAdmin(SaveUtilisateurDTO saveUtilisateurDTO, UserRole userRole) {
        return saveUser(saveUtilisateurDTO,UserRole.ADMIN);
    }

    @Override
    public UtilisateurDTO savePatient(SaveUtilisateurDTO saveUtilisateurDTO, UserRole userRole) {
        return saveUser(saveUtilisateurDTO,UserRole.PATIENT);
    }


    @Override
    public UtilisateurDTO getUserById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Utilisateur Non trouver Avec Id" + id));

        return utilisateurMapper.fromEntity(utilisateur);
    }


    @Override
    public UtilisateurDTO getUserByEmail(String email) {

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);

        UtilisateurDTO utilisateurDTO = utilisateurMapper.fromEntity(utilisateur);

        return utilisateurDTO ;
    }

    @Override
    public UtilisateurDTO getUserByTelephone(String telephone) {
        return null;
    }

    @Override
    public UtilisateurDTO getUserByKeycloakId(String keycloakUserId) {
        return null;
    }


    @Override
    public UtilisateurDTO updateUser(Long id, UpdateUtilisateurDTO updateUtilisateurDTO) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Utilisateur Non trouver avec Id" + id));

        Utilisateur updateUser = utilisateurMapper.partialUpdateUtilisateur(updateUtilisateurDTO, utilisateur);

        utilisateurRepository.save(updateUser);

        //entityEnrichmentService.enrichUtilisateurWithLocality(updateUser);

        return utilisateurMapper.fromEntity(updateUser);
    }


    @Override
    public void deleteUser(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Utilisateur Non trouver Avec Id" + id));
        utilisateurRepository.delete(utilisateur);
        log.info("Utilisateur Supprimer Avec Success");

    }



    @Override
    public List<UtilisateurDTO> getAllUsers() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();

        return utilisateurs.stream()
                .map(utilisateurMapper::fromEntity)
                .collect(Collectors.toList());
    }



    public UserRepresentation getNewUserRepresentation(SaveUtilisateurDTO saveUserDTO, UserRole userRole) {
        if (saveUserDTO == null) {
            return null;
        }
        UserRepresentation userRepresentation = new UserRepresentation();

        // Informations de base
        userRepresentation.setUsername(saveUserDTO.getEmail());
        userRepresentation.setFirstName(saveUserDTO.getPrenom());
        userRepresentation.setLastName(saveUserDTO.getNom());
        // On suppose que cette méthode gère la vérification de l'email
        setEmailToUserRepresentation(userRepresentation, saveUserDTO.getEmail(), true);

        // Activer l'utilisateur : C'est important car l'utilisateur doit pouvoir se connecter
        // pour que Keycloak puisse ensuite le forcer à changer son mot de passe.
        userRepresentation.setEnabled(true);

        // --- MODIFICATIONS CLÉS POUR LE MOT DE PASSE PAR DÉFAUT ET LE CHANGEMENT FORCÉ ---
        String defaultPassword = "1234"; // Définissez ici votre mot de passe par défaut

        // Créer la représentation du mot de passe en le marquant comme TEMPORAIRE (true)
        CredentialRepresentation passwordCred = getPasswordCredentialRepresentation(defaultPassword, true);
        userRepresentation.setCredentials(Collections.singletonList(passwordCred));

        // Ajouter l'action requise "UPDATE_PASSWORD" pour forcer le changement
        userRepresentation.setRequiredActions(Collections.singletonList("UPDATE_PASSWORD"));
        // --- FIN DES MODIFICATIONS CLÉS ---

        return userRepresentation;
    }





    /**
     * Version de getNewUserRepresentation sans paramètre userRole
     * Utilisation recommandée pour la création initiale d'utilisateurs
     */
    public UserRepresentation getNewUserRepresentation(SaveUtilisateurDTO saveUserDTO) {
        return getNewUserRepresentation(saveUserDTO, null);
    }






    public UserRepresentation updateUserRepresentation(UpdateUtilisateurDTO updateUserDTO,
                                                       UserRepresentation existingRepresentation) {
        if (updateUserDTO == null) {
            return existingRepresentation;
        }

        UserRepresentation userRepresentation = existingRepresentation != null ? existingRepresentation
                : new UserRepresentation();

        // Mettre à jour les informations de base
        if (updateUserDTO.getNom() != null) {
            userRepresentation.setFirstName(updateUserDTO.getNom());
        }

        if (updateUserDTO.getPrenom() != null) {
            userRepresentation.setLastName(updateUserDTO.getPrenom());
        }

        return userRepresentation;
    }



    public CredentialRepresentation getPasswordCredentialRepresentation(String password, boolean temporary) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(temporary);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        return passwordCred;
    }

    public void addRealmRoleToUserRepresentation(UserRepresentation userRepresentation, String role) {
        List<String> realmRoles = userRepresentation.getRealmRoles();
        if (realmRoles == null) {
            realmRoles = new ArrayList<>();
        }
        realmRoles.add(role);
        userRepresentation.setRealmRoles(realmRoles);
    }


    /**
     * Met à jour l'utilisateur dans Keycloak
     */
    private void updateUserInKeycloak(Utilisateur user, UpdateUtilisateurDTO updateUserDTO) {
        if (user.getKeycloakId() == null) {
            log.debug("Impossible de mettre à jour l'utilisateur dans Keycloak : ID Keycloak manquant");
            return;
        }

        try {
            // Récupérer la représentation Keycloak existante
            UserRepresentation keycloakUser = keycloakService.getUser(user.getKeycloakId());
            if (keycloakUser != null) {
                // Appliquer les mises à jour
                UserRepresentation updatedKeycloakUser = updateUserRepresentation(updateUserDTO, keycloakUser);

                // Mettre à jour dans Keycloak
                keycloakService.updateUser(user.getKeycloakId(), updatedKeycloakUser);
                log.debug("Utilisateur mis à jour dans Keycloak avec succès: {}", user.getKeycloakId());
            } else {
                log.warn("Utilisateur avec ID Keycloak {} non trouvé dans Keycloak", user.getKeycloakId());
            }
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de l'utilisateur {} dans Keycloak: {}",
                    user.getId(), e.getMessage(), e);
            // Continuer malgré l'erreur, car nous voulons quand même sauvegarder les
            // changements locaux
        }
    }


    @Override
    public UtilisateurDTO addUserRole(Long userId, UserRole userRole) {
        Utilisateur user = getUserWithKeycloakId(userId);

        // Ajouter le rôle dans Keycloak
        modifyUserRole(user, userRole, true);

        // Mettre à jour les rôles locaux depuis Keycloak et sauvegarder
        return updateUserRolesAndSave(user);
    }


    @Override
    public UtilisateurDTO removeUserRole(Long userId, UserRole userRole) {
        Utilisateur user = getUserWithKeycloakId(userId);

        // Supprimer le rôle dans Keycloak
        modifyUserRole(user, userRole, false);

        // Mettre à jour les rôles locaux depuis Keycloak et sauvegarder
        return updateUserRolesAndSave(user);
    }



    /**
     * Récupère un utilisateur et vérifie qu'il possède un ID Keycloak
     */
    private Utilisateur getUserWithKeycloakId(Long userId) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + userId));

        if (user.getKeycloakId() == null || user.getKeycloakId().trim().isEmpty()) {
            throw new ResourceNotFoundException("Utilisateur sans identifiant Keycloak");
        }

        return user;
    }




    /**
     * Ajoute ou supprime un rôle utilisateur dans Keycloak
     *
     * @param user     L'utilisateur concerné
     * @param userRole Le rôle à ajouter ou supprimer
     * @param isAdd    true pour ajouter, false pour supprimer
     */
    private void modifyUserRole(Utilisateur user, UserRole userRole, boolean isAdd) {
        String operation = isAdd ? "Ajout" : "Suppression";
        String keycloakId = user.getKeycloakId();

        log.info("{} du rôle {} pour l'utilisateur Keycloak {}", operation, userRole.name(), keycloakId);

        try {
            if (isAdd) {
                keycloakService.assignRealmRoleToUser(keycloakId, userRole.name());
            } else {
                keycloakService.removeRealmRoleFromUser(keycloakId, userRole.name());
            }
            log.info("Rôle {} {} avec succès pour l'utilisateur {}",
                    userRole.name(), isAdd ? "ajouté" : "supprimé", keycloakId);
        } catch (Exception e) {
            log.error("Erreur lors de l'opération sur le rôle: {}", e.getMessage(), e);
            throw new RuntimeException("Échec de l'" + (isAdd ? "ajout" : "suppression") + " du rôle dans Keycloak", e);
        }
    }



    /**
     * Met à jour les rôles locaux d'un utilisateur depuis Keycloak et sauvegarde
     */
    private UtilisateurDTO updateUserRolesAndSave(Utilisateur user) {
        // Récupérer les rôles mis à jour de Keycloak
        UserRepresentation keycloakUser = keycloakService.getUser(user.getKeycloakId());

        // Mettre à jour l'entité User locale avec les rôles de Keycloak
        user.getRoles().clear(); // Effacer les rôles locaux
        if (keycloakUser != null) {
            populateRolesFromUserRepresentation(keycloakUser, user);
        }

        // Sauvegarder en base de données
        Utilisateur updatedUser = utilisateurRepository.save(user);

        return utilisateurMapper.fromEntity(updatedUser);
    }


    /**
     * Remplit les rôles d'un utilisateur à partir d'une représentation Keycloak
     * Si les rôles ne sont pas définis dans la représentation, ils sont récupérés
     * directement depuis l'API Keycloak
     *
     * @param userRepresentation La représentation de l'utilisateur dans Keycloak
     * @param user               L'entité utilisateur à enrichir avec les rôles
     */
    public void populateRolesFromUserRepresentation(UserRepresentation userRepresentation, Utilisateur user) {
        if (user == null) {
            log.error("Impossible de populer les rôles: l'objet utilisateur est null");
            return;
        }

        // Nettoyer les rôles existants
        user.getRoles().clear();

        List<String> realmRoles = extractRolesFromRepresentation(userRepresentation, user);

        // Ajouter les rôles à l'utilisateur
        if (realmRoles != null && !realmRoles.isEmpty()) {
            for (String role : realmRoles) {
                addRoleToUser(role, user);
            }
        } else {
            log.debug("Aucun rôle n'a été trouvé pour l'utilisateur {} (ID Keycloak: {})", user.getId(),
                    user.getKeycloakId());
        }
    }



    /**
     * Extrait les rôles d'une représentation ou les récupère directement depuis
     * Keycloak
     */
    private List<String> extractRolesFromRepresentation(UserRepresentation userRepresentation, Utilisateur user) {
        // Vérifier d'abord si la représentation contient des rôles
        if (userRepresentation != null && userRepresentation.getRealmRoles() != null
                && !userRepresentation.getRealmRoles().isEmpty()) {
            List<String> roles = userRepresentation.getRealmRoles();
            log.debug("Rôles trouvés dans la représentation utilisateur: {}", String.join(", ", roles));
            return roles;
        }
        // Sinon, récupérer directement depuis Keycloak
        else if (user.getKeycloakId() != null) {
            return fetchRolesDirectlyFromKeycloak(user);
        }

        return Collections.emptyList();
    }



    /**
     * Récupère les rôles directement depuis Keycloak
     */
    private List<String> fetchRolesDirectlyFromKeycloak(Utilisateur user) {
        if (user.getKeycloakId() == null) {
            return Collections.emptyList();
        }

        try {
            // Tenter d'abord d'utiliser la méthode getUser qui garantit l'enrichissement
            UserRepresentation keycloakUser = keycloakService.getUser(user.getKeycloakId());
            if (keycloakUser != null && keycloakUser.getRealmRoles() != null) {
                List<String> roles = keycloakUser.getRealmRoles();
                log.debug("Rôles récupérés via getUser: {}",
                        roles.isEmpty() ? "Aucun" : String.join(", ", roles));
                return roles;
            }
            // Si getUser n'a pas fourni de rôles, essayer la méthode directe
            else {
                // Récupérer directement depuis l'API Keycloak
                List<RoleRepresentation> roles = keycloakService.getUserResource(user.getKeycloakId())
                        .roles().realmLevel().listAll();

                List<String> roleNames = roles.stream()
                        .map(RoleRepresentation::getName)
                        .collect(Collectors.toList());

                log.debug("Rôles récupérés via API directe: {}",
                        roleNames.isEmpty() ? "Aucun" : String.join(", ", roleNames));
                return roleNames;
            }
        } catch (Exception e) {
            log.error("Erreur lors de la récupération directe des rôles: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * Ajoute un rôle à l'utilisateur s'il existe dans l'énumération UserRole
     */
    private void addRoleToUser(String roleName, Utilisateur user) {
        try {
            UserRole userRole = UserRole.valueOf(roleName);
            user.getRoles().add(userRole);
            log.debug("Rôle ajouté à l'utilisateur {}: {}", user.getId(), roleName);
        } catch (IllegalArgumentException e) {
            log.debug("Rôle ignoré (non défini dans l'énumération UserRole): {}", roleName);
        }
    }



    public void setEmailToUserRepresentation(UserRepresentation userRepresentation, String email, boolean verified) {
        userRepresentation.setEmail(email);
        userRepresentation.setEmailVerified(verified);
    }



}