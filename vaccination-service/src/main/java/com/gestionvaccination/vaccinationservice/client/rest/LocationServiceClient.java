package com.gestionvaccination.vaccinationservice.client.rest;

import com.gestionvaccination.vaccinationservice.client.dto.CentreDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.gestionvaccination.vaccinationservice.client.dto.LocationDTO;

import java.util.List;

/**
 * Client Feign pour communiquer avec le location-service
 */
@FeignClient(name = "location-service", path = "/api/v1/localities")
public interface LocationServiceClient {

    /**
     * Récupérer une localité par son ID
     */
    @GetMapping("/api/v1/localities/{id}")
    LocationDTO getLocationById(@PathVariable("id") Long id);

    // Nouvelle méthode pour récupérer tous les centres d'un district
    @GetMapping("/api/v1/centres/by-district/{districtId}")
    List<CentreDTO> getCentresByDistrict(@PathVariable("districtId") Long districtId);
}
