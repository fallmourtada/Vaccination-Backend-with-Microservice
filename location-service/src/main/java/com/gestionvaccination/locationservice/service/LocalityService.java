package com.gestionvaccination.locationservice.service;

import com.gestionvaccination.locationservice.dto.LocalityDTO;
import com.gestionvaccination.locationservice.dto.SaveLocalityDTO;
import com.gestionvaccination.locationservice.dto.UpdateLocalityDTO;
import com.gestionvaccination.locationservice.enumeration.LocalityType;

import java.util.List;

/**
 * Interface du service de gestion des localités
 */
public interface LocalityService {

    /**
     * Get a locality by its ID.
     *
     * @param id the ID of the locality
     * @return the locality
     */
    LocalityDTO getById(Long id);

    /**
     * Create a new locality.
     *
     * @param saveLocalityDTO the locality to create
     * @return the created locality
     */
    LocalityDTO create(SaveLocalityDTO saveLocalityDTO);

    /**
     * Update an existing locality.
     *
     * @return the updated locality
     */
    LocalityDTO update(Long localityToUpdateId, UpdateLocalityDTO updateLocalityDTO);

    /**
     * Delete a locality by its ID.
     *
     * @param id the ID of the locality to delete
     */
    void deleteById(Long id);

    /**
     * Get all localities by type.
     *
     * @param type the type of localities to retrieve
     * @return list of localities of the specified type
     */
    List<LocalityDTO> getAllByType(LocalityType type);

    /**
     * Get all localities that are children of the specified parent.
     *
     * @param parentId the ID of the parent locality
     * @return list of child localities
     */
    List<LocalityDTO> getAllByParent(Long parentId);

    /**
     * Get all localities of a specific type that are children of the specified parent.
     *
     * @param type the type of localities to retrieve
     * @param parentId the ID of the parent locality
     * @return list of child localities of the specified type
     */
    List<LocalityDTO> getAllByTypeAndParent(LocalityType type, Long parentId);

    /**
     * Get all top-level localities of a specific type (those without a parent).
     *
     * @param type the type of localities to retrieve
     * @return list of top-level localities of the specified type
     */
    List<LocalityDTO> getAllTopLevelByType(LocalityType type);

    /**
     * Get all regions.
     *
     * @return list of all regions
     */
    List<LocalityDTO> getAllRegions();

    /**
     * Get all departments.
     *
     * @return list of all departments
     */
    List<LocalityDTO> getAllDepartments();

    /**
     * Get all departments within a specific region.
     *
     * @param regionId the ID of the region
     * @return list of departments in the specified region
     */
    List<LocalityDTO> getDepartmentsByRegion(Long regionId);

    /**
     * Get all districts.
     *
     * @return list of all districts
     */
    List<LocalityDTO> getAllDistricts();

    /**
     * Get all districts within a specific department.
     *
     * @param departmentId the ID of the department
     * @return list of districts in the specified department
     */
    List<LocalityDTO> getDistrictsByDepartment(Long departmentId);

    /**
     * Get all communes.
     *
     * @return list of all communes
     */
    List<LocalityDTO> getAllCommunes();

    /**
     * Get all communes within a specific district.
     *
     * @param districtId the ID of the district
     * @return list of communes in the specified district
     */
    List<LocalityDTO> getCommunesByDistrict(Long districtId);

    // --- NOUVELLES DÉCLARATIONS ---
    List<LocalityDTO> getCommunesByDepartment(Long departmentId);


    List<LocalityDTO> getCommunesByRegion(Long regionId);

    // --- Methode Pour  Récupérer tous les IDs de communes descendantes ---
    List<Long> getCommuneIdsDescendants(Long localityId); // Cette signature est correcte ici
}