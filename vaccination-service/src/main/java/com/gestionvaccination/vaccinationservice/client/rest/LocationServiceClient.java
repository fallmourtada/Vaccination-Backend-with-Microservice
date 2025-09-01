package com.gestionvaccination.vaccinationservice.client.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.gestionvaccination.vaccinationservice.client.dto.LocationDTO;

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
}
