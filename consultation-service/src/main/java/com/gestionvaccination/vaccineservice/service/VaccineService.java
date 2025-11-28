package com.gestionvaccination.vaccineservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.gestionvaccination.vaccineservice.dto.VaccineDTO;
import com.gestionvaccination.vaccineservice.dto.SaveVaccineDTO;
import com.gestionvaccination.vaccineservice.dto.UpdateVaccineDTO;
import com.gestionvaccination.vaccineservice.enumeration.VaccineType;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface du service de gestion des vaccins
 */
public interface VaccineService {
    
    /**
     * Créer un nouveau vaccin
     */
    VaccineDTO saveVaccin(SaveVaccineDTO saveVaccineDTO);
    
    /**
     * Obtenir un vaccin par son ID
     */
    VaccineDTO getVaccinById(Long vaccinId);
    
    /**
     * Mettre à jour un vaccin existant
     */
    VaccineDTO updateVaccin(Long vaccinId, UpdateVaccineDTO updateVaccineDTO);
    
    /**
     * Supprimer un vaccin
     */
    void deleteVaccin(Long vaccinId);
    
    /**
     * Obtenir tous les vaccins avec pagination
     */
    List<VaccineDTO> getAllVaccins();

}
