package com.gestionvaccination.userservice.mapper;

import com.gestionvaccination.userservice.entites.Utilisateur;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.gestionvaccination.userservice.dto.*;
import com.gestionvaccination.userservice.entites.Enfant;
import java.time.LocalDateTime;

/**
 * Mapper pour la conversion entre Enfant et ses DTOs
 */
@Service
@AllArgsConstructor
public class EnfantMapper {


    private final UtilisateurMapper utilisateurMapper;

    /**
     * Convertir une entité Enfant en EnfantDTO
     * L'enrichissement est fait dans le service avant l'appel de cette méthode
     */
    public EnfantDTO fromEntity(Enfant enfant) {
        if (enfant == null)
            return null;

        EnfantDTO enfantDTO = new EnfantDTO();

        if (enfant.getId() != null)
            enfantDTO.setId(enfant.getId());

        if (enfant.getPrenom() != null)
            enfantDTO.setPrenom(enfant.getPrenom());

        if (enfant.getNom() != null)
            enfantDTO.setNom(enfant.getNom());


        if (enfant.getSexe() != null)
            enfantDTO.setSexe(enfant.getSexe());

        if (enfant.getLieuNaissance() != null)
            enfantDTO.setLieuNaissance(enfant.getLieuNaissance());


       if(enfant.getQrCode()!=null)
           enfantDTO.setQrCode(enfant.getQrCode());

        if (enfant.getAllergies() != null)
            enfantDTO.setAllergies(enfant.getAllergies());

        if(enfant.getContenuQrCode()!=null)
            enfantDTO.setContenuQrCcode(enfant.getContenuQrCode());

        if (enfant.getGroupeSanguin() != null)
            enfantDTO.setGroupeSanguin(enfant.getGroupeSanguin());

        if (enfant.getPoids() != null)
            enfantDTO.setPoids(enfant.getPoids());

        if (enfant.getTaille() != null)
            enfantDTO.setTaille(enfant.getTaille());


        if (enfant.getCreatedAt() != null)
            enfantDTO.setCreatedAt(enfant.getCreatedAt());

        if (enfant.getUpdatedAt() != null)
            enfantDTO.setUpdatedAt(enfant.getUpdatedAt());

        if(enfant.getParent() != null)
            enfantDTO.setParent(utilisateurMapper.fromEntity(enfant.getParent()));

        if(enfant.getDateNaissance()!=null)
            enfantDTO.setDateNaissance(enfant.getDateNaissance());

        return enfantDTO;
    }



    /**
     * Ancienne méthode pour rétrocompatibilité - à supprimer après migration complète
     * @deprecated Utiliser la nouvelle méthode avec les DTOs de localisation
     */
    @Deprecated
    public Enfant fromSaveEnfantDTO(SaveEnfantDTO saveEnfantDTO, Utilisateur parent) {
        if (saveEnfantDTO == null) {
            return null;
        }

        Enfant enfant = new Enfant();

        enfant.setPrenom(saveEnfantDTO.getPrenom());
        enfant.setNom(saveEnfantDTO.getNom());
        enfant.setDateNaissance(saveEnfantDTO.getDateNaissance());
        enfant.setSexe(saveEnfantDTO.getSexe());
        enfant.setLieuNaissance(saveEnfantDTO.getLieuNaissance());
        enfant.setGroupeSanguin(saveEnfantDTO.getGroupeSanguin());
        enfant.setPoids(saveEnfantDTO.getPoids());
        enfant.setTaille(saveEnfantDTO.getTaille());
        enfant.setAllergies(saveEnfantDTO.getAllergies());
        //enfant.setContenu_qr_code(saveEnfantDTO.getContenu_qr_code());
        //enfant.setQr_code(saveEnfantDTO.getQr_code());

        if(parent.getId() != null){
            enfant.setParent(parent);
        }else {
            enfant.setParent(null);
        }

        return enfant;
    }


    /**
     * Mettre à jour partiellement un enfant
     */
    public Enfant partialUpdateEnfant(UpdateEnfantDTO updateEnfantDTO, Enfant enfant) {
        if (updateEnfantDTO == null)
            return null;

        if (updateEnfantDTO.getPrenom() != null)
            enfant.setPrenom(updateEnfantDTO.getPrenom());

        if (updateEnfantDTO.getNom() != null)
            enfant.setNom(updateEnfantDTO.getNom());


        if (updateEnfantDTO.getAllergies() != null)
            enfant.setAllergies(updateEnfantDTO.getAllergies());


        if (updateEnfantDTO.getGroupeSanguin() != null)
            enfant.setGroupeSanguin(updateEnfantDTO.getGroupeSanguin());

        if (updateEnfantDTO.getPoids() != null)
            enfant.setPoids(updateEnfantDTO.getPoids());

        if (updateEnfantDTO.getTaille() != null)
            enfant.setTaille(updateEnfantDTO.getTaille());

        return enfant;
    }


}
