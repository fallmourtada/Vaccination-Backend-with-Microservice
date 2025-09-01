package com.gestionvaccination.userservice.client.rest;

import com.gestionvaccination.userservice.client.dto.CentreDTO;
import com.gestionvaccination.userservice.client.dto.LocalityDTO;
import com.gestionvaccination.userservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.gestionvaccination.userservice.client.dto.LocationDTO;

import java.util.List;

/**
 * Client REST pour communiquer avec le Location Service
 */
@FeignClient(
        name = "LOCATION-SERVICE",
        contextId = "userService-localityClient",
        configuration = FeignClientConfig.class
)
public interface LocationServiceClient {
    
    /**
     * Récupérer une localité par son ID
     */
    @GetMapping("/api/v1/localities/{localityId}")
    LocalityDTO getLocality(@PathVariable Long localityId);
    
    /**
     * Récupérer toutes les régions
     */
    @GetMapping("/api/v1/localities/types/regions")
    List<LocationDTO> getAllLocality();


    @GetMapping("/api/v1/localities/centres/{centreId}")
    CentreDTO getCentre(@PathVariable Long centreId);
    

}
