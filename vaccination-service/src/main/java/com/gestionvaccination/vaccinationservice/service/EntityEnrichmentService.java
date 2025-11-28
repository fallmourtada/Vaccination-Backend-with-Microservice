package com.gestionvaccination.vaccinationservice.service;

import com.gestionvaccination.vaccinationservice.client.dto.AppointmentDTO;
import com.gestionvaccination.vaccinationservice.client.dto.EnfantDTO;
import com.gestionvaccination.vaccinationservice.client.dto.UtilisateurDTO;
import com.gestionvaccination.vaccinationservice.client.dto.VaccineDTO;
import com.gestionvaccination.vaccinationservice.client.rest.AppointmentServiceClient;
import com.gestionvaccination.vaccinationservice.client.rest.UserServiceClient;
import com.gestionvaccination.vaccinationservice.client.rest.VaccinServiceClient;
import com.gestionvaccination.vaccinationservice.entity.Vaccination;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class EntityEnrichmentService {
    private final UserServiceClient userServiceClient;
    private final VaccinServiceClient vaccinServiceClient;
    private final AppointmentServiceClient appointmentServiceClient;


    /**
     * Enrichit un utilisateur avec sa localité assignée
     *
     * @param vaccination L'utilisateur à enrichir
     */
    public void enrichVaccinationWithAppointment(Vaccination vaccination) {
        if (vaccination == null || vaccination.getAppointmentId() == null) {
            return;
        }

        try {
            AppointmentDTO appointment = appointmentServiceClient.getAppointmentById(vaccination.getAppointmentId());
            vaccination.setAppointment(appointment);

        } catch (feign.FeignException.NotFound e) {
            log.warn("Vaccination avec l'ID {} non trouvée pour le Rendez Vous {}",
                    vaccination.getId(), vaccination.getAppointmentId());

        } catch (Exception e) {
            log.error("Erreur lors de la récupération du Rendez Vous {}",
                    vaccination.getAppointmentId(), e);
            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
        }
    }




    /**
     * Enrichit une liste d'utilisateurs avec leurs localités assignées
     *
     * @param vaccination Liste d'utilisateurs à enrichir
     */
    public void enrichVaccinationWithVaccin(Vaccination vaccination) {
        if (vaccination == null || vaccination.getVaccineId() == null) {
            return;
        }

        try {
            VaccineDTO vaccin = vaccinServiceClient.getVaccinById(vaccination.getVaccineId());
            vaccination.setVaccine(vaccin);

        } catch (feign.FeignException.NotFound e) {
            log.warn("Vaccin avec l'ID {} non trouvée pour la Vaccination {}",
                    vaccination.getVaccineId(), vaccination.getId());

        } catch (Exception e) {
            log.error("Erreur lors de la récupération du Vaccin{}",
                    vaccination.getVaccine(), e);
            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
        }
    }







//    public void enrichVaccinationWithUser(Vaccination vaccination) {
//        if(vaccination == null || vaccination.getUserId() == null) {
//            return;
//        }
//
//        try {
//            UtilisateurDTO user = userServiceClient.getUser(vaccination.getUserId());
//            vaccination.setUtilisateur(user);
//
//        } catch (feign.FeignException.NotFound e) {
//            log.warn("Utilisateur Avec l'id {} non trouvée pour la Vaccination {}",
//                    vaccination.getUserId(), vaccination.getId());
//
//            // Ne définit pas la localité sur null pour ne pas perdre l'ID de référence
//        } catch (Exception e) {
//            log.error("Erreur lors de la récupération l'utilisateur avec l'ID: {}",
//                    vaccination.getUserId(), e);
//            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
//        }
//    }


    public void enrichVaccinationWithInfirmier(Vaccination vaccination) {
        if(vaccination == null || vaccination.getInfirmierId()== null) {
            return;
        }

        try {
            UtilisateurDTO infirmier = userServiceClient.getUser(vaccination.getInfirmierId());
            vaccination.setInfirmier(infirmier);

        } catch (feign.FeignException.NotFound e) {
            log.warn("Infirmier Avec l'id {} non trouvée pour la Vaccination {}",
                    vaccination.getInfirmierId(), vaccination.getId());

            // Ne définit pas la localité sur null pour ne pas perdre l'ID de référence
        } catch (Exception e) {
            log.error("Erreur lors de la récupération l'infirmier avec l'ID: {}",
                    vaccination.getInfirmierId(), e);
            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
        }
    }

    public void enrichVaccinationWithParent(Vaccination vaccination) {
        if(vaccination == null || vaccination.getParentId()== null) {
            return;
        }

        try {
            UtilisateurDTO parent = userServiceClient.getUser(vaccination.getParentId());
            vaccination.setParent(parent);
        } catch (feign.FeignException.NotFound e) {
            log.warn("Parent Avec l'id {} non trouvée pour la Vaccination {}",
                    vaccination.getParentId(), vaccination.getId());

            // Ne définit pas la localité sur null pour ne pas perdre l'ID de référence
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du Parent avec l'ID: {}",
                    vaccination.getParentId(), e);
            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
        }
    }


    public void enrichVaccinationWithEnfant(Vaccination vaccination){
        if(vaccination == null || vaccination.getEnfantId() == null) {
            return;
        }

        try {
            EnfantDTO  enfant = userServiceClient.getEnfantById(vaccination.getEnfantId());
            vaccination.setEnfant(enfant);

        } catch (feign.FeignException.NotFound e) {
            log.warn("Vaccination Avec l'id {} non trouvée pour l'enfant {}",
                    vaccination.getId(), vaccination.getEnfantId());

            // Ne définit pas la localité sur null pour ne pas perdre l'ID de référence
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'enfant avec l'ID: {}",
                    vaccination.getEnfantId(), e);
            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
        }

    }

    public void enrichVaccinationsWithEnfants(Collection<Vaccination> vaccinations){
        if (vaccinations == null || vaccinations.isEmpty()) {
            return;
        }
        vaccinations.forEach(this::enrichVaccinationWithEnfant);

    }


//    public void enrichVaccinationsWithUtilisateurs(Collection<Vaccination> vaccinations ) {
//        if (vaccinations == null || vaccinations.isEmpty()) {
//            return;
//        }
//
//        vaccinations.forEach(this::enrichVaccinationWithUser);
//    }

    public void enrichVaccinationsWithInfirmiers(Collection<Vaccination> vaccinations) {
        if(vaccinations == null || vaccinations.isEmpty()) {
            return;
        }

        vaccinations.forEach(this::enrichVaccinationWithInfirmier);
    }

    public void enrichVaccinationsWithParents(Collection<Vaccination> vaccinations) {
        if(vaccinations == null || vaccinations.isEmpty()) {
            return;
        }
        vaccinations.forEach(this::enrichVaccinationWithParent);
    }


    public void enrichVaccinationsWithVaccins(Collection<Vaccination> vaccinations ) {
        if (vaccinations == null || vaccinations.isEmpty()) {
            return;
        }

        vaccinations.forEach(this::enrichVaccinationWithVaccin);
    }


    public void enrichVaccinationsWithAppointments(Collection<Vaccination> vaccinations ) {
        if (vaccinations == null || vaccinations.isEmpty()) {
            return;
        }

        vaccinations.forEach(this::enrichVaccinationWithAppointment);
    }

    public void enrichVaccinationWithAllData(Vaccination vaccination) {
        if(vaccination == null) {
            return;
        }

        this.enrichVaccinationWithVaccin(vaccination);
        //this.enrichVaccinationWithUser(vaccination);
        this.enrichVaccinationWithAppointment(vaccination);
        this.enrichVaccinationWithEnfant(vaccination);
        this.enrichVaccinationWithInfirmier(vaccination);
        this.enrichVaccinationWithParent(vaccination);
    }



    public void enrichVaccinationsWithAllData(Collection<Vaccination> vaccinations) {
        if (vaccinations == null || vaccinations.isEmpty()) {
            return;
        }

        vaccinations.forEach(this::enrichVaccinationWithAllData);
    }







}
