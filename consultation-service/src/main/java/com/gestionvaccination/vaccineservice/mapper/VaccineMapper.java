package com.gestionvaccination.vaccineservice.mapper;

import com.gestionvaccination.vaccineservice.enumeration.VaccineType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import com.gestionvaccination.vaccineservice.entity.Vaccine;
import com.gestionvaccination.vaccineservice.dto.VaccineDTO;
import com.gestionvaccination.vaccineservice.dto.SaveVaccineDTO;
import com.gestionvaccination.vaccineservice.dto.UpdateVaccineDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir entre les entités Vaccine et les DTOs
 */
@Service
@AllArgsConstructor
public class VaccineMapper {

    /**
     * Convertir une entité Vaccine en VaccineDTO
     */
    public VaccineDTO fromEntity(Vaccine vaccine) {
        if (vaccine == null) {
            return null;
        }

        VaccineDTO vaccineDTO = new VaccineDTO();
        vaccineDTO.setId(vaccine.getId());
        vaccineDTO.setNom(vaccine.getNom());
        vaccineDTO.setFabricant(vaccine.getFabricant());
        vaccineDTO.setNumeroLot(vaccine.getNumeroLot());
        vaccineDTO.setDateProduction(vaccine.getDateProduction());
        vaccineDTO.setDateExpiration(vaccine.getDateExpiration());
        vaccineDTO.setDescription(vaccine.getDescription());
        vaccineDTO.setTypeVaccin(vaccine.getTypeVaccin());
        vaccineDTO.setModeAdministration(vaccine.getModeAdministration());
        vaccineDTO.setTemperatureConservation(vaccine.getTemperatureConservation());
        vaccineDTO.setEffetsSecondaires(vaccine.getEffetsSecondaires());
        //vaccineDTO.setQuantiteDisponible(vaccine.getQuantiteDisponible());
        vaccineDTO.setPeriodePrise(vaccine.getPeriodePrise());
        vaccineDTO.setAAdministrer(vaccine.getAAdministrer());
        vaccineDTO.setEffetsSecondaires(vaccine.getEffetsSecondaires());
        vaccineDTO.setLieuAdministration(vaccine.getLieuAdministration());
        vaccineDTO.setDuree(vaccine.getDuree());
        vaccineDTO.setPrecautionsAPrendre(vaccine.getPrecautionsAPrendre());
        vaccineDTO.setAAdministrer(vaccine.getAAdministrer());
        vaccineDTO.setDuree(vaccine.getDuree());
        vaccineDTO.setLieuAdministration(vaccine.getLieuAdministration());
        vaccineDTO.setDosesAdministres(vaccine.getDosesAdministres());
        vaccineDTO.setPrecautionsAPrendre(vaccine.getPrecautionsAPrendre());
        vaccineDTO.setProtection(vaccine.getProtection());


        return vaccineDTO;
    }



    /**
     * Convertir un SaveVaccineDTO en entité Vaccine en appliquant
     * une logique de remplissage conditionnelle basée sur le TypeVaccin
     * et en explicitant la mise à NULL des champs non pertinents.
     */
    public Vaccine fromSaveVaccineDTO(SaveVaccineDTO saveVaccineDTO) {
        if (saveVaccineDTO == null) {
            return null;
        }

        // 1. Initialisation et champs COMMUMS à tous les vaccins
        Vaccine vaccine = new Vaccine();
        vaccine.setNom(saveVaccineDTO.getNom());
        vaccine.setTypeVaccin(saveVaccineDTO.getTypeVaccin());
        vaccine.setDescription(saveVaccineDTO.getDescription());

        if (saveVaccineDTO.getTypeVaccin() == VaccineType.ENFANT) {
            // ----------------------------------------------------
            // --- 2. Logique pour TypeVaccin.ENFANT (Logistique) ---
            // ----------------------------------------------------

            // Renseignement des détails Logistiques et d'Administration
            vaccine.setFabricant(saveVaccineDTO.getFabricant());
            vaccine.setNumeroLot(saveVaccineDTO.getNumeroLot());
            vaccine.setDateProduction(saveVaccineDTO.getDateProduction());
            vaccine.setDateExpiration(saveVaccineDTO.getDateExpiration());
            vaccine.setTemperatureConservation(saveVaccineDTO.getTemperatureConservation());
            vaccine.setModeAdministration(saveVaccineDTO.getModeAdministration());
            vaccine.setLieuAdministration(saveVaccineDTO.getLieuAdministration());
            vaccine.setPeriodePrise(saveVaccineDTO.getPeriodePrise());
            vaccine.setDosesAdministres(saveVaccineDTO.getDosesAdministres());
            vaccine.setEffetsSecondaires(saveVaccineDTO.getEffetsSecondaires());

            // Explicitons la mise à NULL des champs PARENT
            vaccine.setAAdministrer(null);
            vaccine.setProtection(null);
            vaccine.setDuree(null);
            vaccine.setPrecautionsAPrendre(null);

        } else if (saveVaccineDTO.getTypeVaccin() == VaccineType.PARENT) {
            // -----------------------------------------------------------------
            // --- 3. Logique pour TypeVaccin.PARENT (Programme/Recommandation) ---
            // -----------------------------------------------------------------

            // Renseignement des détails Programme/Recommandation
            vaccine.setAAdministrer(saveVaccineDTO.getAAdministrer());
            vaccine.setProtection(saveVaccineDTO.getProtection());
            vaccine.setDuree(saveVaccineDTO.getDuree());
            vaccine.setPrecautionsAPrendre(saveVaccineDTO.getPrecautionsAPrendre());

            // Explicitons la mise à NULL des champs ENFANT
            vaccine.setFabricant(null);
            vaccine.setNumeroLot(null);
            vaccine.setDateProduction(null);
            vaccine.setDateExpiration(null);
            vaccine.setTemperatureConservation(null);
            vaccine.setModeAdministration(null);
            vaccine.setLieuAdministration(null);
            vaccine.setPeriodePrise(null);
            vaccine.setDosesAdministres(null);
            vaccine.setEffetsSecondaires(null);
        }

        // Note : Le champ 'dosage' est présent dans le DTO mais commenté dans l'entité 'Vaccine'.
        // Nous ne pouvons pas le mapper, mais il n'est pas nécessaire de le mettre à null ici.

        return vaccine;
    }


    /**
     * Mettre à jour une entité Vaccine avec les données d'un UpdateVaccineDTO
     */
    public Vaccine updatePartialVaccineDTO(UpdateVaccineDTO updateVaccineDTO, Vaccine vaccine) {
        if (updateVaccineDTO == null || vaccine == null) {
            return null;
        }

        if (updateVaccineDTO.getNom() != null) {
            vaccine.setNom(updateVaccineDTO.getNom());
        }
        if (updateVaccineDTO.getFabricant() != null) {
            vaccine.setFabricant(updateVaccineDTO.getFabricant());
        }
        if (updateVaccineDTO.getNumeroLot() != null) {
            vaccine.setNumeroLot(updateVaccineDTO.getNumeroLot());
        }
        if (updateVaccineDTO.getDateProduction() != null) {
            vaccine.setDateProduction(updateVaccineDTO.getDateProduction());
        }
        if (updateVaccineDTO.getDateExpiration() != null) {
            vaccine.setDateExpiration(updateVaccineDTO.getDateExpiration());
        }
        if (updateVaccineDTO.getDescription() != null) {
            vaccine.setDescription(updateVaccineDTO.getDescription());
        }

        if (updateVaccineDTO.getTypeVaccin() != null) {
            vaccine.setTypeVaccin(updateVaccineDTO.getTypeVaccin());
        }
        if (updateVaccineDTO.getModeAdministration() != null) {
            vaccine.setModeAdministration(updateVaccineDTO.getModeAdministration());
        }
        if (updateVaccineDTO.getTemperatureConservation() != null) {
            vaccine.setTemperatureConservation(updateVaccineDTO.getTemperatureConservation());
        }


        if (updateVaccineDTO.getEffetsSecondaires() != null) {
            vaccine.setEffetsSecondaires(updateVaccineDTO.getEffetsSecondaires());
        }

        if(updateVaccineDTO.getLieuAdministration() != null) {
            vaccine.setLieuAdministration(updateVaccineDTO.getLieuAdministration());
        }

        if(updateVaccineDTO.getDuree() != null) {
            vaccine.setDuree(updateVaccineDTO.getDuree());
        }

        if(updateVaccineDTO.getPrecautionsAPrendre() != null) {
            vaccine.setPrecautionsAPrendre(updateVaccineDTO.getPrecautionsAPrendre());
        }

        if(updateVaccineDTO.getDosesAdministres() != null) {
            vaccine.setDosesAdministres(updateVaccineDTO.getDosesAdministres());
        }


        return vaccine;

    }
}
