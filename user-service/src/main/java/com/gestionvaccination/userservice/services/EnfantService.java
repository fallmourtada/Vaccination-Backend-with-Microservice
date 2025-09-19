package com.gestionvaccination.userservice.services;

import com.gestionvaccination.userservice.client.dto.EnfantAvecVaccinationsDTO;
import com.gestionvaccination.userservice.dto.*;
import com.gestionvaccination.userservice.enumeration.Sexe;
import com.google.zxing.WriterException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface du service de gestion des enfants
 */
public interface EnfantService {
    
    /**
     * Créer un nouvel enfant
     */
    EnfantDTO saveEnfant(SaveEnfantDTO saveEnfantDTO,Long parentId) throws IOException, WriterException;
    
    /**
     * Obtenir un enfant par ID
     */
    EnfantDTO getEnfantById(Long enfantId);
    
    /**
     * Obtenir un enfant par code QR
     */
    EnfantDTO getEnfantByCodeQr(String codeQr);
    
    /**
     * Mettre à jour un enfant
     */
    EnfantDTO updateEnfant(Long enfantId, UpdateEnfantDTO updateEnfantDTO);
    
    /**
     * Supprimer un enfant
     */
    void deleteEnfant(Long enfantId);
    
    /**
     * Obtenir les enfants d'un parent
     */
    //List<EnfantDTO> getEnfantByParentId(Long parentId);

    /**
     * Obtenir tous les enfants avec pagination
     */
    List<EnfantDTO> getAllEnfants();


    EnfantAvecVaccinationsDTO getEnfantByQrCode(String codeQr);


    EnfantAvecVaccinationsDTO getEnfantWithVaccinations(Long enfantId);

    EnfantAvecVaccinationsDTO getEnfantWithVaccinationsByQrCode(String qrCode);

}
