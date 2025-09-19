package com.gestionvaccination.userservice.servicesImpl;


import com.gestionvaccination.userservice.client.dto.CentreDTO;
import com.gestionvaccination.userservice.client.dto.LocalityDTO;
import com.gestionvaccination.userservice.client.rest.AuthServiceClient;
import com.gestionvaccination.userservice.client.rest.LocationServiceClient;
import com.gestionvaccination.userservice.dto.*;
import com.gestionvaccination.userservice.entites.Utilisateur;
import com.gestionvaccination.userservice.enumeration.UserRole;
import com.gestionvaccination.userservice.exception.ResourceNotFoundException;
import com.gestionvaccination.userservice.mapper.UtilisateurMapper;
import com.gestionvaccination.userservice.repository.UtilisateurRepository;
import com.gestionvaccination.userservice.services.EntityEnrichmentService;
import com.gestionvaccination.userservice.services.UtilisateurService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    private final UtilisateurMapper utilisateurMapper;

    private final EntityEnrichmentService entityEnrichmentService;

    private final LocationServiceClient locationServiceClient;

    private final AuthServiceClient authServiceClient;

    @Override
    public UtilisateurDTO saveUtilisateur(SaveUtilisateurDTO saveUtilisateurDTO, UserRole userRole,Long localityId,Long centreId) {

        LocalityDTO locality = null;
        if (localityId != null) {
            try {
                locality = locationServiceClient.getLocality(localityId); // Récupère la commune
                if (locality== null) {
                    throw new ResourceNotFoundException("Localité non trouvée avec l'ID: " + localityId);
                }
//                user.setLocalityId(localityId);
//                user.setAssignedLocality(assignedLocality);
            } catch (Exception e) {
                log.error("Erreur lors de la récupération de la localité avec ID {}: {}", localityId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération de la localité: " + e.getMessage());
            }
        }

        CentreDTO centre=null;
        if(centreId!=null){
            try{
                centre = locationServiceClient.getCentre(centreId);
                if(centre==null){
                    throw new ResourceNotFoundException("centre Non trouver Avec Id: " + centreId);
                }


            }catch (Exception e){
                log.error("Erreur lors de la récupération du Centre avec ID {}: {}", centreId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération du Centre: " + e.getMessage());
            }
        }

        Utilisateur user = utilisateurMapper.fromSaveUtilisateurDTO(saveUtilisateurDTO, locality,centre);
        user.setLocalityId(localityId);
        user.setLocality(locality);
        user.setCentreId(centreId);
        user.setCentre(centre);
        user.setUserRole(userRole);
        Utilisateur savedUser = utilisateurRepository.save(user);
        entityEnrichmentService.enrichUtilisateurWithLocality(savedUser);
        //entityEnrichmentService.enrichUtilisateurWithLocality(savedUser);
        UtilisateurDTO userDTO = utilisateurMapper.fromEntity(savedUser);
        userDTO.setLocality(locality);
        userDTO.setCentre(centre);



        return userDTO;
    }



    @Override
    public UtilisateurDTO saveInfirmier(SaveInfirmierDTO saveInfirmierDTO, UserRole userRole, Long centreId) {
        CentreDTO centre=null;
        if(centreId!=null){
            try{
                centre = locationServiceClient.getCentre(centreId);
                if(centre==null){
                    throw new ResourceNotFoundException("centre Non trouver Avec Id: " + centreId);
                }


            }catch (Exception e){
                log.error("Erreur lors de la récupération du Centre avec ID {}: {}", centreId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération du Centre: " + e.getMessage());
            }
        }

        Utilisateur utilisateur = utilisateurMapper.fromSavingInfirmier(saveInfirmierDTO,centre);
        utilisateur.setUserRole(userRole);
        Utilisateur savedUser = utilisateurRepository.save(utilisateur);
        entityEnrichmentService.enrichUtilisateurWithAllData(utilisateur);
        UtilisateurDTO userDTO = utilisateurMapper.fromEntity(savedUser);
        userDTO.setCentre(centre);

        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setUsername(saveInfirmierDTO.getEmail());
        authRequestDTO.setPassword(saveInfirmierDTO.getPassword());
        authRequestDTO.setRole(userRole.name());
        authServiceClient.SaveInfirmier(authRequestDTO);

        return userDTO;
    }

    @Override
    public UtilisateurDTO saveParent(SaveParentDTO saveParentDTO, UserRole userRole, Long centreId) {
        CentreDTO centre=null;
        if(centreId!=null){
            try{
                centre = locationServiceClient.getCentre(centreId);
                if(centre==null){
                    throw new ResourceNotFoundException("centre Non trouver Avec Id: " + centreId);
                }


            }catch (Exception e){
                log.error("Erreur lors de la récupération du Centre avec ID {}: {}", centreId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération du Centre: " + e.getMessage());
            }
        }

        Utilisateur utilisateur = utilisateurMapper.fromSavingParentDTO(saveParentDTO,centre);
        utilisateur.setUserRole(userRole);
        Utilisateur savedUser = utilisateurRepository.save(utilisateur);
//        entityEnrichmentService.enrichUtilisateurWithCentre(utilisateur);
        UtilisateurDTO userDTO = utilisateurMapper.fromEntity(savedUser);
        userDTO.setCentre(centre);

        return userDTO;
    }


    @Override
    public UtilisateurDTO getUserById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Utilisateur Non trouver Avec Id" + id));

        entityEnrichmentService.enrichUtilisateurWithAllData(utilisateur);

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

        entityEnrichmentService.enrichUtilisateursWithAllData(utilisateurs);

        return utilisateurs.stream()
                .map(utilisateurMapper::fromEntity)
                .collect(Collectors.toList());
    }



}