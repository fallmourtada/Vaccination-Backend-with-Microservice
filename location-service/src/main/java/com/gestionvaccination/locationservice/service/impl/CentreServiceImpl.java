package com.gestionvaccination.locationservice.service.impl;

import com.gestionvaccination.locationservice.dto.CentreDTO;
import com.gestionvaccination.locationservice.dto.SaveCentreDTO;
import com.gestionvaccination.locationservice.dto.UpdateCentreDTO;
import com.gestionvaccination.locationservice.entity.Centre;
import com.gestionvaccination.locationservice.exception.ResourceNotFoundException;
import com.gestionvaccination.locationservice.mapper.CentreMapper;
import com.gestionvaccination.locationservice.repository.CentreRepository;
import com.gestionvaccination.locationservice.service.CentreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class CentreServiceImpl implements CentreService {
    private final CentreRepository centreRepository;
    private final CentreMapper centreMapper;


    @Override
    public CentreDTO getCentreById(Long centreId) {
        Centre centre = centreRepository.findById(centreId)
                .orElseThrow(()->new ResourceNotFoundException("Centre non trouver avec Id"+centreId));

        return centreMapper.fromEntity(centre);
    }



    @Override
    public List<CentreDTO> getAllCentre() {
        List<Centre> centres = centreRepository.findAll();

        List<CentreDTO> centreDTOS = centres.stream().map(centreMapper::fromEntity)
                .collect(Collectors.toList());

            return centreDTOS;
    }



    @Override
    public CentreDTO saveCentre(SaveCentreDTO saveCentreDTO) {
        Centre centre = centreMapper.fromSavingCentreDTO(saveCentreDTO);

        Centre centre1 = centreRepository.save(centre);

        return centreMapper.fromEntity(centre1);
    }



    @Override
    public void deleteCentreById(Long centreId) {
        Centre centre = centreRepository.findById(centreId)
                .orElseThrow(()->new ResourceNotFoundException("Centre non trouver avec Id"+centreId));
        centreRepository.delete(centre);


    }



    @Override
    public CentreDTO updateCentre(Long centreId, UpdateCentreDTO updateCentreDTO) {
        Centre centre = centreRepository.findById(centreId)
                .orElseThrow(()->new ResourceNotFoundException("Centre non trouver avec Id"+centreId));

        Centre centre1 = centreMapper.partialUpdate(updateCentreDTO, centre);

        return centreMapper.fromEntity(centre1);
    }


    @Override
    /**
     * Récupère une liste de tous les centres rattachés à un district spécifique.
     * Cette méthode est utilisée pour les calculs de statistiques par district.
     * @param districtId L'identifiant du district.
     * @return Une liste de {@link CentreDTO} pour tous les centres du district.
     */
    public List<CentreDTO> getCentresByDistrict(Long districtId) {
        return centreRepository.findByParentId(districtId).stream()
                .map(centreMapper::fromEntity) // Utilisez une méthode de conversion pour retourner des DTO
                .collect(Collectors.toList());
    }
}
