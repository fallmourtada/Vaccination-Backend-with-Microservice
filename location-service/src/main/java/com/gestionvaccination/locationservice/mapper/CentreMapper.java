package com.gestionvaccination.locationservice.mapper;

import com.gestionvaccination.locationservice.dto.CentreDTO;
import com.gestionvaccination.locationservice.dto.SaveCentreDTO;
import com.gestionvaccination.locationservice.dto.UpdateCentreDTO;
import com.gestionvaccination.locationservice.entity.Centre;
import com.gestionvaccination.locationservice.entity.Locality;
import com.gestionvaccination.locationservice.exception.ResourceNotFoundException;
import com.gestionvaccination.locationservice.repository.CentreRepository;
import com.gestionvaccination.locationservice.repository.LocalityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CentreMapper {
    private final LocalityMapper localityMapper;
    private final LocalityRepository localityRepository;
    private final CentreRepository centreRepository;

    public CentreDTO fromEntity(Centre centre) {
        if(centre == null) return null;

        CentreDTO centreDTO = new CentreDTO();
        centreDTO.setId(centre.getId());
        centreDTO.setName(centre.getName());
        centreDTO.setPhone(centre.getPhone());
        centreDTO.setType(centre.getType());

        if(centre.getParent() != null)
            centreDTO.setParent(fromEntity(centre.getParent()));

        if(centre.getLocality()!=null)
            centreDTO.setLocality(localityMapper.fromEntity(centre.getLocality()));

        if(centre.getCreatedAt() != null)
            centreDTO.setCreatedAt(centre.getCreatedAt());

        if(centre.getUpdatedAt() != null)
            centreDTO.setUpdatedAt(centre.getUpdatedAt());

        return centreDTO;
    }


//    public Centre fromSavingCentreDTO(SaveCentreDTO saveCentreDTO) {
//        if(saveCentreDTO == null) return null;
//
//        Centre centre = new Centre();
//        centre.setName(saveCentreDTO.getName());
//        centre.setType(saveCentreDTO.getType());
//        centre.setPhone(saveCentreDTO.getPhone());
//
//        Long localityId = saveCentreDTO.getLocationId();
//        Locality locality = localityRepository.findById(localityId)
//                .orElseThrow(()->new ResourceNotFoundException("Location non trouver avec Id"+localityId));
//
//        if(centre.getLocality()!=null){
//            centre.setLocality(locality);
//        }else {
//            centre.setLocality(null);
//        }
//
//        Long parentId = saveCentreDTO.getParentId();
//        Centre centre1 = centreRepository.findById(parentId)
//                .orElseThrow(()->new ResourceNotFoundException("Parent non trouver avec Id"+parentId));
//
//        if(centre.getParent()!=null){
//            centre.setParent(centre1);
//        }else  {
//            centre.setParent(null);
//        }
//
//        return centre;
//
//
//    }

    public Centre fromSavingCentreDTO(SaveCentreDTO saveCentreDTO) {
        if (saveCentreDTO == null) {
            return null;
        }

        Centre centre = new Centre();
        centre.setName(saveCentreDTO.getName());
        centre.setType(saveCentreDTO.getType());
        centre.setPhone(saveCentreDTO.getPhone());

        // Gérer la localité (locationId)
        // On ne cherche la localité que si l'ID existe
        Long localityId = saveCentreDTO.getLocationId();
        if (localityId != null) {
            Locality locality = localityRepository.findById(localityId)
                    .orElseThrow(() -> new ResourceNotFoundException("Localité non trouvée avec l'ID: " + localityId));
            centre.setLocality(locality);
        }
        // Si localityId est null, le champ 'locality' reste null, ce qui est le comportement souhaité.

        // Gérer le parent (parentId)
        // On ne cherche le parent que si l'ID est fourni
        Long parentId = saveCentreDTO.getParentId();
        if (parentId != null) {
            Centre parentCentre = centreRepository.findById(parentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Centre parent non trouvé avec l'ID: " + parentId));
            centre.setParent(parentCentre);
        }
        // Si parentId est null, le champ 'parent' reste null.

        return centre;
    }


    public Centre partialUpdate(UpdateCentreDTO updateCentreDTO,Centre centre){
        if(updateCentreDTO == null || centre == null) return null;

        if(updateCentreDTO.getName()!=null)
            centre.setName(updateCentreDTO.getName());

        if(updateCentreDTO.getPhone()!=null)
            centre.setPhone(updateCentreDTO.getPhone());

        if(updateCentreDTO.getType()!=null)
            centre.setType(updateCentreDTO.getType());

        return centre;
    }
}
