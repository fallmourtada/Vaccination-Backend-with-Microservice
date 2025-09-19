package com.gestionvaccination.vaccinationservice.service.impl;

import com.gestionvaccination.vaccinationservice.client.dto.*;
import com.gestionvaccination.vaccinationservice.client.enumeration.StatutRv;
import com.gestionvaccination.vaccinationservice.client.rest.AppointmentServiceClient;
import com.gestionvaccination.vaccinationservice.client.rest.UserServiceClient;
import com.gestionvaccination.vaccinationservice.client.rest.VaccinServiceClient;
import com.gestionvaccination.vaccinationservice.entity.Vaccination;
import com.gestionvaccination.vaccinationservice.exception.ResourceNotFoundException;
import com.gestionvaccination.vaccinationservice.service.EntityEnrichmentService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gestionvaccination.vaccinationservice.service.VaccinationService;
import com.gestionvaccination.vaccinationservice.repository.VaccinationRepository;
import com.gestionvaccination.vaccinationservice.mapper.VaccinationMapper;
import com.gestionvaccination.vaccinationservice.dto.VaccinationDTO;
import com.gestionvaccination.vaccinationservice.dto.SaveVaccinationDTO;
import com.gestionvaccination.vaccinationservice.dto.UpdateVaccinationDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j

public class VaccinationServiceImpl implements VaccinationService {
    private final VaccinationRepository vaccinationRepository;
    private final VaccinationMapper vaccinationMapper;
    private final EntityEnrichmentService entityEnrichmentService;
    private final AppointmentServiceClient appointmentServiceClient;
    private final UserServiceClient userServiceClient;
    private final VaccinServiceClient vaccinServiceClient;



//    @Override
//    public VaccinationDTO saveVaccination(SaveVaccinationDTO saveVaccinationDTO) {
//        return null;
//    }

    @Override
    public VaccinationDTO saveVaccination(SaveVaccinationDTO saveVaccinationDTO, Long vaccinId, Long appointmentId,  Long userId,Long enfantId) {
        AppointmentDTO appointment=null;
        if(appointmentId!=null){
            try{
                appointment = appointmentServiceClient.getAppointmentById(appointmentId);
                if(appointment==null){
                    throw new ResourceNotFoundException("Rendez Vous  Non trouver Avec Id: " + appointmentId);
                }


            }catch (Exception e){
                log.error("Erreur lors de la récupération du RendezVous avec ID {}: {}", appointmentId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération du RendezVous: " + e.getMessage());
            }
        }

        VaccineDTO  vaccin=null;
        if(vaccinId!=null){
            try{
                vaccin = vaccinServiceClient.getVaccinById(vaccinId);
                if(vaccin==null){
                    throw new ResourceNotFoundException("Vaccin  Non trouver Avec Id: " + vaccinId);
                }


            }catch (Exception e){
                log.error("Erreur lors de la récupération du Vaccin avec ID {}: {}", vaccinId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération du Vaccin: " + e.getMessage());
            }
        }


        UtilisateurDTO  utilisateur=null;
        if(userId!=null){
            try{
                utilisateur = userServiceClient.getUser(userId);
                if(utilisateur==null){
                    throw new ResourceNotFoundException("Infirmier Non trouver Avec Id: " + userId);
                }


            }catch (Exception e){
                log.error("Erreur lors de la récupération de l'infirmier  avec ID {}: {}", userId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération de l'infirmier: " + e.getMessage());
            }
        }

        EnfantDTO  enfant=null;
        if(enfantId!=null){
            try{
                enfant = userServiceClient.getEnfantById(enfantId);
                if(enfant==null){
                    throw new ResourceNotFoundException("Enfant Non trouver Avec Id: " + enfantId);
                }


            }catch (Exception e){
                log.error("Erreur lors de la récupération de l'enfant  avec ID {}: {}", enfantId, e.getMessage());
                throw new ResourceNotFoundException("Erreur lors de la récupération de l'enfant: " + e.getMessage());
            }
        }



       Vaccination vaccination  =  vaccinationMapper.fromSaveVaccination(saveVaccinationDTO,utilisateur,appointment,vaccin,enfant);

        Vaccination vaccination1 = vaccinationRepository.save(vaccination);

        UpdateStatutAppointmentDTO updateStatutAppointmentDTO = new UpdateStatutAppointmentDTO();
        updateStatutAppointmentDTO.setStatut(StatutRv.EFFECTUER);
        appointmentServiceClient.updateStatuAppointment(appointmentId,updateStatutAppointmentDTO);



        entityEnrichmentService.enrichVaccinationWithAllData(vaccination1);

        return vaccinationMapper.fromEntity(vaccination1);
    }

    @Override
    public VaccinationDTO getVaccinationById(Long vaccinationId) {
        Vaccination vaccination = vaccinationRepository.findById(vaccinationId)
                .orElseThrow(()-> new ResourceNotFoundException("Vaccination non trouver  avec id: " + vaccinationId));
        entityEnrichmentService.enrichVaccinationWithAllData(vaccination);

        return vaccinationMapper.fromEntity(vaccination);
    }

    @Override
    public VaccinationDTO updateVaccination(Long vaccinationId, UpdateVaccinationDTO updateVaccinationDTO) {
        Vaccination vaccination = vaccinationRepository.findById(vaccinationId)
                .orElseThrow(()-> new ResourceNotFoundException("Vaccination non trouver  avec id: " + vaccinationId));
        Vaccination vaccination1 = vaccinationMapper.partialUpdate(updateVaccinationDTO, vaccination);

        entityEnrichmentService.enrichVaccinationWithAllData(vaccination1);

        return vaccinationMapper.fromEntity(vaccination1);
    }



    @Override
    public List<VaccinationDTO> getAllVaccinations() {
        List<Vaccination> vaccinations = vaccinationRepository.findAll();

        List<VaccinationDTO> vaccinationDTOS = vaccinations.stream()
                .map(vaccinationMapper::fromEntity)
                .collect(Collectors.toList());

        return vaccinationDTOS;
    }


    @Override
    public void deleteVaccination(Long vaccinationId) {
        Vaccination vaccination = vaccinationRepository.findById(vaccinationId)
                .orElseThrow(()-> new ResourceNotFoundException("Vaccination non trouver  avec id: " + vaccinationId));

        vaccinationRepository.delete(vaccination);
        log.info("Vaccination Supprimer Avec Success");

    }

    @Override
    public List<VaccinationDTO> getVaccinationByEnfant(Long enfantId) {
        List<Vaccination> vaccinations = vaccinationRepository.getVaccinationByEnfantId(enfantId);

        entityEnrichmentService.enrichVaccinationsWithAllData(vaccinations);

        List<VaccinationDTO> vaccinationDTOS = vaccinations.stream()
                .map(vaccinationMapper::fromEntity)
                .collect(Collectors.toList());


        return vaccinationDTOS;
    }

    @Override
    public List<VaccinationDTO> getVaccinationByVaccin(Long vaccineId) {
        return List.of();
    }

    @Override
    public List<VaccinationDTO> getVaccinationByCentre(Long centreId) {
        return List.of();
    }

    @Override
    public List<VaccinationDTO> getVaccinationsByEnfantQrCode(String qrCode) {
        try {

            EnfantDTO enfant = userServiceClient.getEnfantByQrCode(qrCode);

            if (enfant == null) {
                throw new ResourceNotFoundException("Enfant non trouvé avec le code QR: " + qrCode);
            }

            Long enfantId = enfant.getId();
            // 2. Récupérer la liste des vaccinations en utilisant l'enfantId
            List<Vaccination> vaccinations = vaccinationRepository.getVaccinationByEnfantId(enfantId);
            List<VaccinationDTO> vaccinationDTOS = vaccinations.stream().
                    map(vaccinationMapper::fromEntity).
                    collect(Collectors.toList());

            return vaccinationDTOS;

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des vaccinations par code QR {}: {}", qrCode, e.getMessage());
            throw new RuntimeException("Impossible de récupérer les vaccinations pour ce QR Code.", e);
        }

    }
}
