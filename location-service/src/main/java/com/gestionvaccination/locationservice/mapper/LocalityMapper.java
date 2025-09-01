package com.gestionvaccination.locationservice.mapper;

import com.gestionvaccination.locationservice.dto.LocalityDTO;
import com.gestionvaccination.locationservice.dto.SaveLocalityDTO;
import com.gestionvaccination.locationservice.dto.UpdateLocalityDTO;
import com.gestionvaccination.locationservice.entity.Locality;
import org.springframework.stereotype.Service;

/**
 * Mapper pour la conversion entre Locality et ses DTOs
 */
@Service
public class LocalityMapper {

    /**
     * Convertir une entité Locality en LocalityDTO
     */
    public LocalityDTO fromEntity(Locality locality) {
        if (locality == null) {
            return null;
        }
        
        LocalityDTO localityDTO = new LocalityDTO();
        localityDTO.setId(locality.getId());
        localityDTO.setName(locality.getName());
        localityDTO.setCodification(locality.getCodification());
        localityDTO.setType(locality.getType());
        localityDTO.setCreatedAt(locality.getCreatedAt());
        localityDTO.setUpdatedAt(locality.getUpdatedAt());
        
        // Mapper le parent (sans récursion profonde pour éviter les cycles)
        if (locality.getParent() != null) {
            localityDTO.setParent(fromEntity(locality.getParent()));
        }
        
        return localityDTO;
    }

    /**
     * Convertir un SaveLocalityDTO en entité Locality
     */
    public Locality fromSaveDTO(SaveLocalityDTO saveLocalityDTO) {
        if (saveLocalityDTO == null) {
            return null;
        }
        
        Locality locality = new Locality();
        locality.setName(saveLocalityDTO.getName());
        locality.setType(saveLocalityDTO.getType());
        locality.setCodification(saveLocalityDTO.getCodification());
         // Par défaut, une nouvelle localité est active
        
        return locality;
    }

    /**
     * Mise à jour partielle d'une entité Locality avec un UpdateLocalityDTO
     */
    public Locality partialUpdate(UpdateLocalityDTO updateLocalityDTO, Locality locality) {
        if (updateLocalityDTO == null || locality == null) {
            return locality;
        }
        
        if (updateLocalityDTO.getName() != null) {
            locality.setName(updateLocalityDTO.getName());
        }
        if (updateLocalityDTO.getType() != null) {
            locality.setType(updateLocalityDTO.getType());
        }
        if (updateLocalityDTO.getCodification() != null) {
            locality.setCodification(updateLocalityDTO.getCodification());
        }
        
        return locality;
    }
}
