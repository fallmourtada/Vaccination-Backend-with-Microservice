package com.gestionvaccination.locationservice.service;

import com.gestionvaccination.locationservice.dto.CentreDTO;
import com.gestionvaccination.locationservice.dto.SaveCentreDTO;
import com.gestionvaccination.locationservice.dto.UpdateCentreDTO;

import java.util.List;

public interface CentreService {

    public CentreDTO getCentreById(Long centreId);

    public List<CentreDTO> getAllCentre();

    public CentreDTO saveCentre(SaveCentreDTO saveCentreDTO);

    public void deleteCentreById(Long centreId);

    public CentreDTO updateCentre(Long centreId, UpdateCentreDTO updateCentreDTO);

    public List<CentreDTO> findByParentId(Long parentId);
}
