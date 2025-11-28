package com.gestionclinique.userservice.mapper;

import com.gestionclinique.userservice.dto.SaveUtilisateurDTO;
import com.gestionclinique.userservice.dto.UpdateUtilisateurDTO;
import com.gestionclinique.userservice.dto.UtilisateurDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gestionclinique.userservice.entites.Utilisateur;
//import com.gestionvaccination.userservice.services.EntityEnrichmentService;


/**
 * Mapper pour la conversion entre Utilisateur et ses DTOs
 */
@Service
@AllArgsConstructor
public class UtilisateurMapper {

    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    //private final EnfantMapper enfantMapper;

    /**
     * Convertir une entité Utilisateur en UtilisateurDTO
     */
    public UtilisateurDTO fromEntity(Utilisateur utilisateur) {
        if (utilisateur == null)
            return null;

        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();

        if (utilisateur.getId() != null)
            utilisateurDTO.setId(utilisateur.getId());

        if (utilisateur.getEmail() != null)
            utilisateurDTO.setEmail(utilisateur.getEmail());

        if (utilisateur.getTelephone() != null)
            utilisateurDTO.setTelephone(utilisateur.getTelephone());


        if (utilisateur.getAge() != null)
            utilisateurDTO.setAge(utilisateur.getAge());

        if (utilisateur.getPrenom() != null)
            utilisateurDTO.setPrenom(utilisateur.getPrenom());

        if (utilisateur.getNom() != null)
            utilisateurDTO.setNom(utilisateur.getNom());

        if (utilisateur.getAdresse() != null)
            utilisateurDTO.setAdresse(utilisateur.getAdresse());


        if (utilisateur.getProfession() != null)
            utilisateurDTO.setProfession(utilisateur.getProfession());


//        if (utilisateur.getCreatedAt() != null)
//            utilisateurDTO.setCreatedAt(utilisateur.getCreatedAt());
//
//        if (utilisateur.getUpdatedAt() != null)
//            utilisateurDTO.setUpdatedAt(utilisateur.getUpdatedAt());



        if(utilisateur.getUserRole()!=null)
            utilisateurDTO.setUserRole(utilisateur.getUserRole());

        if(utilisateur.getMatricule()!=null)
            utilisateurDTO.setMatricule(utilisateur.getMatricule());

        if(utilisateur.getKeycloakId()!=null)
            utilisateurDTO.setKeycloakId(utilisateur.getKeycloakId());



        return utilisateurDTO;
    }



    /**
     * Convertir un SaveUtilisateurDTO en entité Utilisateur
     */
    public Utilisateur fromSaveUtilisateurDTO(SaveUtilisateurDTO saveUtilisateurDTO) {
        if (saveUtilisateurDTO == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setEmail(saveUtilisateurDTO.getEmail());
        utilisateur.setTelephone(saveUtilisateurDTO.getTelephone());
        utilisateur.setAge(saveUtilisateurDTO.getAge());
        utilisateur.setPrenom(saveUtilisateurDTO.getPrenom());
        utilisateur.setNom(saveUtilisateurDTO.getNom());
        utilisateur.setAdresse(saveUtilisateurDTO.getAdresse());
        utilisateur.setProfession(saveUtilisateurDTO.getProfession());
        utilisateur.setMatricule(saveUtilisateurDTO.getMatricule());



        return utilisateur;
    }


    /**
     * Mettre à jour partiellement un utilisateur
     */
    public Utilisateur partialUpdateUtilisateur(UpdateUtilisateurDTO updateUtilisateurDTO, Utilisateur utilisateur) {
        if (updateUtilisateurDTO == null)
            return null;

        if (updateUtilisateurDTO.getTelephone() != null)
            utilisateur.setTelephone(updateUtilisateurDTO.getTelephone());

        if (updateUtilisateurDTO.getPrenom() != null)
            utilisateur.setPrenom(updateUtilisateurDTO.getPrenom());

        if (updateUtilisateurDTO.getNom() != null)
            utilisateur.setNom(updateUtilisateurDTO.getNom());

        if (updateUtilisateurDTO.getAdresse() != null)
            utilisateur.setAdresse(updateUtilisateurDTO.getAdresse());


        if (updateUtilisateurDTO.getProfession() != null)
            utilisateur.setProfession(updateUtilisateurDTO.getProfession());

        if(updateUtilisateurDTO.getAge()!=null)
            utilisateur.setAge(updateUtilisateurDTO.getAge());


        return utilisateur;

    }




}
