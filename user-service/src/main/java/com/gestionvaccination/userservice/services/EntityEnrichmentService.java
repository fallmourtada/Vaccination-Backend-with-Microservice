package com.gestionvaccination.userservice.services;

import com.gestionvaccination.userservice.client.dto.CentreDTO;
import com.gestionvaccination.userservice.client.dto.LocalityDTO;
import com.gestionvaccination.userservice.client.dto.VaccinationDTO;
import com.gestionvaccination.userservice.client.rest.LocationServiceClient;
import com.gestionvaccination.userservice.entites.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class EntityEnrichmentService {
    private final LocationServiceClient locationServiceClient;


    /**
     * Enrichit un utilisateur avec sa localité assignée
     *
     * @param user L'utilisateur à enrichir
     */
    public void enrichUtilisateurWithLocality(Utilisateur user) {
        if (user == null || user.getLocalityId() == null) {
            return;
        }

        try {
            LocalityDTO locality = locationServiceClient.getLocality(user.getLocalityId());
            user.setLocality(locality);
        } catch (feign.FeignException.NotFound e) {
            log.warn("Localité avec l'ID {} non trouvée pour l'utilisateur {}",
                    user.getLocalityId(), user.getId());
            // Ne définit pas la localité sur null pour ne pas perdre l'ID de référence
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de la localité avec l'ID: {}",
                    user.getLocalityId(), e);
            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
        }
    }




    /**
     * Enrichit une liste d'utilisateurs avec leurs localités assignées
     *
     * @param users Liste d'utilisateurs à enrichir
     */
    public void enrichUtilisateursWithLocalities(Collection<Utilisateur> users) {
        if (users == null || users.isEmpty()) {
            return;
        }

        users.forEach(this::enrichUtilisateurWithLocality);
    }



    public void enrichUtilisateurWithCentre(Utilisateur user) {
        if(user == null || user.getCentreId() == null) {
            return;
        }

        try {
            CentreDTO  centre = locationServiceClient.getCentre(user.getCentreId());
            user.setCentre(centre);

        } catch (feign.FeignException.NotFound e) {
            log.warn("Centre avec l'ID {} non trouvée pour l'utilisateur {}",
                    user.getCentreId(), user.getId());

            // Ne définit pas la localité sur null pour ne pas perdre l'ID de référence
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du Centre avec l'ID: {}",
                    user.getCentreId(), e);
            // Ne lève pas d'exception pour éviter de bloquer l'ensemble de la requête
        }
    }


    public void enrichUtilisateursWithCentre(Collection<Utilisateur> users) {
        if (users == null || users.isEmpty()) {
            return;
        }

        users.forEach(this::enrichUtilisateurWithCentre);
    }


    public void enrichUtilisateurWithAllData(Utilisateur user) {
        if(user == null) {
            return;
        }

        this.enrichUtilisateurWithLocality(user);
        this.enrichUtilisateurWithCentre(user);
    }


    public void enrichUtilisateursWithAllData(Collection<Utilisateur> users) {
        if (users == null || users.isEmpty()) {
            return;
        }

        users.forEach(this::enrichUtilisateurWithAllData);

    }


    public void enrichEnfantWithVaccination(VaccinationDTO vaccinationDTO) {

    }



}
