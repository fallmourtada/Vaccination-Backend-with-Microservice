package com.gestionvaccination.vaccineservice.mapper;

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
        vaccineDTO.setPeriode(vaccine.getPeriode());
        
        return vaccineDTO;
    }




    /**
     * Convertir un SaveVaccineDTO en entité Vaccine
     */
    public Vaccine fromSaveVaccineDTO(SaveVaccineDTO saveVaccineDTO) {
        if (saveVaccineDTO == null) {
            return null;
        }

        Vaccine vaccine = new Vaccine();
        vaccine.setNom(saveVaccineDTO.getNom());
        vaccine.setFabricant(saveVaccineDTO.getFabricant());
        vaccine.setNumeroLot(saveVaccineDTO.getNumeroLot());
        vaccine.setDateProduction(saveVaccineDTO.getDateProduction());
        vaccine.setDateExpiration(saveVaccineDTO.getDateExpiration());
        vaccine.setDescription(saveVaccineDTO.getDescription());
        vaccine.setTypeVaccin(saveVaccineDTO.getTypeVaccin());
        vaccine.setModeAdministration(saveVaccineDTO.getModeAdministration());
        vaccine.setTemperatureConservation(saveVaccineDTO.getTemperatureConservation());
        vaccine.setEffetsSecondaires(saveVaccineDTO.getEffetsSecondaires());
        vaccine.setDosesRequises(saveVaccineDTO.getDosesRequises());
//        vaccine.setQuantiteDisponible(saveVaccineDTO.getQuantiteDisponible());
        vaccine.setPeriode(saveVaccineDTO.getPeriode());
        
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

        if (updateVaccineDTO.getDosesRequises() != null) {
            vaccine.setDosesRequises(updateVaccineDTO.getDosesRequises());
        }

//        if (updateVaccineDTO.getQuantiteDisponible() != null) {
//            vaccine.setQuantiteDisponible(updateVaccineDTO.getQuantiteDisponible());
//        }
        return vaccine;
    }
}
