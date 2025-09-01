package com.gestionvaccination.vaccineservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gestionvaccination.vaccineservice.dto.VaccineDTO;
import com.gestionvaccination.vaccineservice.dto.SaveVaccineDTO;
import com.gestionvaccination.vaccineservice.dto.UpdateVaccineDTO;
import com.gestionvaccination.vaccineservice.entity.Vaccine;
import com.gestionvaccination.vaccineservice.enumeration.VaccineType;
import com.gestionvaccination.vaccineservice.exception.ResourceNotFoundException;
import com.gestionvaccination.vaccineservice.mapper.VaccineMapper;
import com.gestionvaccination.vaccineservice.repository.VaccineRepository;
import com.gestionvaccination.vaccineservice.service.VaccineService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des vaccins
 */
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class VaccineServiceImpl implements VaccineService {
    private final VaccineRepository vaccineRepository;
    private final VaccineMapper vaccineMapper;

    @Override
    public VaccineDTO saveVaccin(SaveVaccineDTO saveVaccineDTO) {
       Vaccine vaccine =  vaccineMapper.fromSaveVaccineDTO(saveVaccineDTO);
       Vaccine vaccine1 = vaccineRepository.save(vaccine);
        return vaccineMapper.fromEntity(vaccine1);
    }

    @Override
    public VaccineDTO getVaccinById(Long vaccinId) {
        Vaccine vaccine = vaccineRepository.findById(vaccinId)
                .orElseThrow(()->new ResourceNotFoundException("Vaccin Non Trouver Avec Id"+vaccinId));
        return vaccineMapper.fromEntity(vaccine);
    }

    @Override
    public VaccineDTO updateVaccin(Long vaccinId, UpdateVaccineDTO updateVaccineDTO) {
        Vaccine vaccine = vaccineRepository.findById(vaccinId)
                .orElseThrow(()->new ResourceNotFoundException("Vaccin Non Trouver Avec Id"+vaccinId));

        Vaccine vaccine1 = vaccineMapper.updatePartialVaccineDTO(updateVaccineDTO,vaccine);
        vaccineRepository.save(vaccine1);
        return vaccineMapper.fromEntity(vaccine1);
    }

    @Override
    public void deleteVaccin(Long vaccinId) {
        Vaccine vaccine = vaccineRepository.findById(vaccinId)
                .orElseThrow(()->new ResourceNotFoundException("Vaccin Non Trouver Avec Id"+vaccinId));
        vaccineRepository.delete(vaccine);
        log.info("Vaccin Supprimer Avec Success");

    }


    @Override
    public List<VaccineDTO> getAllVaccins() {
        List<Vaccine> vaccines = vaccineRepository.findAll();

        return vaccines.stream().map(vaccine ->
                vaccineMapper.fromEntity(vaccine)).
                collect(Collectors.toList());
    }

}