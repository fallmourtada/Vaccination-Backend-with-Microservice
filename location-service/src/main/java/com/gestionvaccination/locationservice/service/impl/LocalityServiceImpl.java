package com.gestionvaccination.locationservice.service.impl;

import com.gestionvaccination.locationservice.dto.LocalityDTO;
import com.gestionvaccination.locationservice.dto.SaveLocalityDTO;
import com.gestionvaccination.locationservice.dto.UpdateLocalityDTO;
import com.gestionvaccination.locationservice.entity.Locality;
import com.gestionvaccination.locationservice.enumeration.LocalityType;
import com.gestionvaccination.locationservice.exception.ResourceNotFoundException;
import com.gestionvaccination.locationservice.mapper.LocalityMapper;
import com.gestionvaccination.locationservice.repository.LocalityRepository;
import com.gestionvaccination.locationservice.service.LocalityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des localités
 */
@Service
@AllArgsConstructor
@Transactional
public class LocalityServiceImpl implements LocalityService {


    private final LocalityRepository localityRepository;
    private final LocalityMapper localityMapper;

    @Override
    public LocalityDTO getById(Long id) {
        Locality locality = localityRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Locality not found with id: " + id));
        return localityMapper.fromEntity(locality);
    }

    @Override
    public LocalityDTO create(SaveLocalityDTO localityDTO) {
        Locality locality;
        locality = localityMapper.fromSaveDTO(localityDTO);

        if (localityDTO.getParentId() != null) {
            Locality parentLocality = localityRepository.findById(localityDTO.getParentId())
                    .orElseThrow(()-> new ResourceNotFoundException("Parent locality not found with id: " + localityDTO.getParentId()));
            locality.setParent(parentLocality);
        }
        locality = localityRepository.save(locality);
        return localityMapper.fromEntity(locality);
    }

    @Override
    public LocalityDTO update(Long localityToUpdateId, UpdateLocalityDTO updateLocalityDTO) {
        Locality locality = localityRepository.findById(localityToUpdateId)
                .orElseThrow(()-> new ResourceNotFoundException("Locality not found with id: " + localityToUpdateId));

        locality = localityMapper.partialUpdate(updateLocalityDTO, locality);

        if (updateLocalityDTO.getParentId() != null) {
            Locality parentLocality = localityRepository.findById(updateLocalityDTO.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent locality not found with id: " + updateLocalityDTO.getParentId()));
            locality.setParent(parentLocality);
        }
        locality = localityRepository.save(locality);
        return localityMapper.fromEntity(locality);
    }

    @Override
    public void deleteById(Long id) {
        if (!localityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Locality not found with id: " + id);
        }
        localityRepository.deleteById(id);
    }

    @Override
    public List<LocalityDTO> getAllByType(LocalityType type) {
        return localityRepository.findAllByType(type).stream()
                .map(localityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalityDTO> getAllByParent(Long parentId) {
        validateLocalityExists(parentId);
        return localityRepository.findAllByParentId(parentId).stream()
                .map(localityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalityDTO> getAllByTypeAndParent(LocalityType type, Long parentId) {
        validateLocalityExists(parentId);
        return localityRepository.findAllByTypeAndParentId(type, parentId).stream()
                .map(localityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalityDTO> getAllTopLevelByType(LocalityType type) {
        return localityRepository.findAllByTypeWithNoParent(type).stream()
                .map(localityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalityDTO> getAllRegions() {
        return getAllByType(LocalityType.REGION);
    }

    @Override
    public List<LocalityDTO> getAllDepartments() {
        return getAllByType(LocalityType.DEPARTMENT);
    }

    @Override
    public List<LocalityDTO> getDepartmentsByRegion(Long regionId) {
        validateLocalityTypeAndExists(regionId, LocalityType.REGION);
        return getAllByTypeAndParent(LocalityType.DEPARTMENT, regionId);
    }

    @Override
    public List<LocalityDTO> getAllDistricts() {
        return getAllByType(LocalityType.DISTRICT);
    }

    @Override
    public List<LocalityDTO> getDistrictsByDepartment(Long departmentId) {
        validateLocalityTypeAndExists(departmentId, LocalityType.DEPARTMENT);
        return getAllByTypeAndParent(LocalityType.DISTRICT, departmentId);
    }

    @Override
    public List<LocalityDTO> getAllCommunes() {
        return getAllByType(LocalityType.COMMUNE);
    }

    @Override
    public List<LocalityDTO> getCommunesByDistrict(Long districtId) {
        validateLocalityTypeAndExists(districtId, LocalityType.DISTRICT);
        return getAllByTypeAndParent(LocalityType.COMMUNE, districtId);
    }

    // --- NOUVELLES MÉTHODES POUR LE FILTRAGE DES CAISSES ---

    /**
     * Récupère toutes les communes appartenant à un département donné.
     * D'après votre structure, une COMMUNE peut être un enfant direct d'un DEPARTMENT.
     */
    public List<LocalityDTO> getCommunesByDepartment(Long departmentId) {
        validateLocalityTypeAndExists(departmentId, LocalityType.DEPARTMENT);
        // Utilise la méthode existante, car le département est le parent direct de la commune dans ce cas.
        return localityRepository.findAllByTypeAndParentId(LocalityType.COMMUNE, departmentId).stream()
                .map(localityMapper::fromEntity)
                .collect(Collectors.toList());
    }


    /**
     * Récupère toutes les communes appartenant à une région donnée.
     * Nécessite la nouvelle requête dans le repository.
     */
    public List<LocalityDTO> getCommunesByRegion(Long regionId) {
        validateLocalityTypeAndExists(regionId, LocalityType.REGION);
        return localityRepository.findCommunesByRegionId(regionId).stream()
                .map(localityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    private void validateLocalityExists(Long localityId) {
        if (!localityRepository.existsById(localityId)) {
            throw new ResourceNotFoundException("Locality not found with id: " + localityId);
        }
    }

    private void validateLocalityTypeAndExists(Long localityId, LocalityType expectedType) {
        Locality locality = localityRepository.findById(localityId)
                .orElseThrow(() -> new ResourceNotFoundException("Locality not found with id: " + localityId));

        if (locality.getType() != expectedType) {
            throw new IllegalArgumentException("Locality with id: " + localityId + " is not of type: " + expectedType);
        }
    }


    // --- NOUVELLE Methode : Récupérer tous les IDs de communes descendantes ---

    @Override
    @Transactional(readOnly = true)
    public List<Long> getCommuneIdsDescendants(Long startId) {
        List<Locality> all = localityRepository.findAll();

        Map<Long, List<Locality>> tree = all.stream()
                .filter(l -> l.getParent() != null)
                .collect(Collectors.groupingBy(l -> l.getParent().getId()));

        List<Long> communeIds = new ArrayList<>();
        collectCommuneIds(startId, tree, all.stream()
                .collect(Collectors.toMap(Locality::getId, l -> l)), communeIds);
        return communeIds;
    }

    private void collectCommuneIds(Long currentId, Map<Long, List<Locality>> tree,
                                   Map<Long, Locality> lookup, List<Long> result) {
        Locality curr = lookup.get(currentId);
        if (curr == null) return;
        if (curr.getType() == LocalityType.COMMUNE) {
            result.add(curr.getId());
            return;
        }
        List<Locality> children = tree.getOrDefault(currentId, Collections.emptyList());
        children.forEach(child ->
                collectCommuneIds(child.getId(), tree, lookup, result));
    }

}
