package com.gestionvaccination.vaccinationservice.service;

import com.gestionvaccination.vaccinationservice.dto.VaccinationDTO;
import com.gestionvaccination.vaccinationservice.dto.SaveVaccinationDTO;
import com.gestionvaccination.vaccinationservice.dto.UpdateVaccinationDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface du service de gestion des vaccinations
 */
public interface VaccinationService {
    VaccinationDTO saveVaccination(SaveVaccinationDTO saveVaccinationDTO,Long vaccinId,Long appointmentId,Long userId,Long enfantId);

    VaccinationDTO getVaccinationById(Long vaccinationId);

    VaccinationDTO updateVaccination(Long vaccinationId, UpdateVaccinationDTO updateVaccinationDTO);

    List<VaccinationDTO>  getAllVaccinations();

    void deleteVaccination(Long vaccinationId);

    List<VaccinationDTO> getVaccinationByEnfant(Long enfantId);

    List<VaccinationDTO> getVaccinationByVaccin(Long vaccineId);

    List<VaccinationDTO> getVaccinationByCentre(Long centreId);

}
