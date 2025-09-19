package com.gestionvaccination.userservice.servicesImpl;

import com.gestionvaccination.userservice.client.dto.EnfantAvecVaccinationsDTO;
import com.gestionvaccination.userservice.client.dto.VaccinationDTO;
import com.gestionvaccination.userservice.client.rest.VaccinationServiceClient;
import com.gestionvaccination.userservice.entites.QrCodeGenerator;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gestionvaccination.userservice.dto.*;
import com.gestionvaccination.userservice.entites.Enfant;
import com.gestionvaccination.userservice.entites.Utilisateur;
import com.gestionvaccination.userservice.exception.ResourceNotFoundException;
import com.gestionvaccination.userservice.mapper.EnfantMapper;
import com.gestionvaccination.userservice.repository.EnfantRepository;
import com.gestionvaccination.userservice.repository.UtilisateurRepository;
import com.gestionvaccination.userservice.services.EnfantService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des enfants
 */
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class EnfantServiceImpl implements EnfantService {
    private final EnfantRepository enfantRepository;

    private final EnfantMapper enfantMapper;

    //private final EntityEnrichmentService entityEnrichmentService;

    private final UtilisateurRepository utilisateurRepository;

    private final VaccinationServiceClient vaccinationServiceClient; // 🔥 injecte ton client Feign


    // Fonction Pour creer le dossier pour les qr_codes
    private void ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }


//    @Override
//    public EnfantDTO saveEnfant(SaveEnfantDTO saveEnfantDTO,Long parentId) throws IOException, WriterException {
//
//        Utilisateur utilisateur = utilisateurRepository.findById(parentId).orElseThrow(
//                () -> new ResourceNotFoundException("parent non Trouver Avec Id" + parentId));
//
//        Enfant enfant = enfantMapper.fromSaveEnfantDTO(saveEnfantDTO, utilisateur);
//
//        // Génération de l'accessToken et du QR Code uniquement pour les nouveaux utilisateurs
////        String accessToken = UUID.randomUUID().toString();
////        String contenuQRCode =  accessToken;
////        String directoryPath = "qr_codes";
////        ensureDirectoryExists(directoryPath);
////        String filePath = directoryPath + "/enfant_" + saveEnfantDTO.getPrenom()+ '-'+ saveEnfantDTO.getNom() +'_'+ saveEnfantDTO.getParentId() + "_qr.png";
////        QrCodeGenerator.generateQRCode(contenuQRCode, filePath);
////        enfant.setQrCode(filePath);
////        enfant.setContenuQrCode(contenuQRCode);
//
//
//            // Contenu du QR code
//            String contenuQRCode = "Nom: " + enfant.getNom() +
//                    " Prenom" + enfant.getPrenom() +
//                    " Date De Naissance: " + enfant.getDateNaissance()+
//                    " Prenom Parent : " + enfant.getParent().getPrenom()+
//                    " Nom Parent : " + enfant.getParent().getNom()+
//                    " Telephone : " + enfant.getParent().getTelephone()+
//                    " Adresse : " + enfant.getParent().getAdresse();
//
//            // Générer le fichier QR code
//            String filePath = "qr_codes/etudiant_" + enfant.getParent().getTelephone() + "_qr.png";
//            QrCodeGenerator.generateQRCode(contenuQRCode, filePath);
//
//            // Ajouter les informations de QR code à l'entité Étudiant
//            enfant.setContenuQrCode(contenuQRCode);
//            enfant.setQrCode(filePath);
//
//
//
//
//
//
//
//        Enfant enfant1 = enfantRepository.save(enfant);
//
//        return enfantMapper.fromEntity(enfant1);
//    }



        @Override
        public EnfantDTO saveEnfant(SaveEnfantDTO saveEnfantDTO, Long parentId) throws IOException, WriterException {

            Utilisateur utilisateur = utilisateurRepository.findById(parentId).orElseThrow(
                    () -> new ResourceNotFoundException("Parent non trouvé avec Id " + parentId));

            Enfant enfant = enfantMapper.fromSaveEnfantDTO(saveEnfantDTO, utilisateur);

            // Sauvegarde initiale pour obtenir l'ID généré
            enfant = enfantRepository.save(enfant);

            // ⚡ Utiliser l'ID de l'enfant comme contenu du QR code
            String contenuQRCode = enfant.getId().toString();

            // Générer le fichier QR code
            String directoryPath = "qr_codes";
            ensureDirectoryExists(directoryPath);
            String filePath = directoryPath + "/enfant_" + enfant.getId() + "_qr.png";
            QrCodeGenerator.generateQRCode(contenuQRCode, filePath);

            enfant.setContenuQrCode(contenuQRCode);
            enfant.setQrCode(filePath);

            // Sauvegarde finale avec le QR code
            enfant = enfantRepository.save(enfant);

            return enfantMapper.fromEntity(enfant);
        }





    @Override
    public EnfantDTO getEnfantById(Long enfantId) {
        Enfant enfant = enfantRepository.findById(enfantId)
                .orElseThrow(() -> new ResourceNotFoundException("Enfant non trouv<UNK> Avec  Id" + enfantId));
        return enfantMapper.fromEntity(enfant);
    }






    @Override
    public EnfantDTO getEnfantByCodeQr(String codeQr) {
        Enfant enfant = enfantRepository.findByContenuQrCode(codeQr)
                .orElseThrow( () ->new ResourceNotFoundException("Enfant non trouvé avec le code QR: " + codeQr));


        return enfantMapper.fromEntity(enfant);
    }




    @Override
    public EnfantDTO updateEnfant(Long enfantId, UpdateEnfantDTO updateEnfantDTO) {
        Enfant enfant = enfantRepository.findById(enfantId)
                .orElseThrow(() -> new ResourceNotFoundException("Enfant non trouv<UNK> Avec  Id" + enfantId));

        Enfant enfant1 = enfantMapper.partialUpdateEnfant(updateEnfantDTO, enfant);
        enfantRepository.save(enfant1);

        return enfantMapper.fromEntity(enfant1);
    }



    @Override
    public void deleteEnfant(Long enfantId) {
        Enfant enfant = enfantRepository.findById(enfantId)
                .orElseThrow(() -> new ResourceNotFoundException("Enfant non trouv<UNK> Avec  Id" + enfantId));
        enfantRepository.delete(enfant);
        log.info("Enfant Supprimer Avec Success");

    }

//    @Override
//    public List<EnfantDTO> getEnfantByParentId(Long parentId) {
//        Utilisateur utilisateur = utilisateurRepository.findById(parentId)
//                .orElseThrow(() -> new ResourceNotFoundException("Parent Non trouver Avec Id" + parentId));
//
//        List<Enfant> enfants = enfantRepository.findByParentId(utilisateur.getId());
//
//        return enfants.stream().map(enfant ->
//                        enfantMapper.fromEntity(enfant)).
//                collect(Collectors.toList());
//    }

//    @Override
//    public Page<EnfantDTO> obtenirEnfantsParParent(Long parentId, Pageable pageable) {
//        return null;
//    }

    @Override
    public List<EnfantDTO> getAllEnfants() {
        List<Enfant> enfants = enfantRepository.findAll();

        return enfants.stream().map(enfant ->
                        enfantMapper.fromEntity(enfant)).
                collect(Collectors.toList());
    }


    @Override
    public EnfantAvecVaccinationsDTO getEnfantByQrCode(String codeQr) {
        Enfant enfant = enfantRepository.findByContenuQrCode(codeQr)
                .orElseThrow(() -> new ResourceNotFoundException("Enfant non trouvé avec le code QR: " + codeQr));

        EnfantDTO enfantDTO = enfantMapper.fromEntity(enfant);

        // 🔥 Appel au VaccinationService
        List<VaccinationDTO> vaccinations = vaccinationServiceClient.getVaccinationsByQrCode(codeQr);

        return new EnfantAvecVaccinationsDTO(enfantDTO, vaccinations);
    }


    public EnfantAvecVaccinationsDTO getEnfantWithVaccinations(Long enfantId) {
        EnfantDTO enfant = enfantRepository.findById(enfantId)
                .map(enfantMapper::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Enfant non trouvé avec id " + enfantId));

        List<VaccinationDTO> vaccinations = vaccinationServiceClient.getVaccinationsByEnfant(enfantId);

        EnfantAvecVaccinationsDTO dto = new EnfantAvecVaccinationsDTO();
        dto.setEnfant(enfant);
        dto.setVaccinations(vaccinations);

        return dto;
    }

    public EnfantAvecVaccinationsDTO getEnfantWithVaccinationsByQrCode(String qrCode) {
        Long enfantId = Long.parseLong(qrCode); // car ton QR code stocke juste l'id
        return getEnfantWithVaccinations(enfantId);
    }



}