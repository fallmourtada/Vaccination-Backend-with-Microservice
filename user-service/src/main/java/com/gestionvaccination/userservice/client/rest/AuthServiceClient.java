package com.gestionvaccination.userservice.client.rest;

import com.gestionvaccination.userservice.client.dto.CentreDTO;
import com.gestionvaccination.userservice.client.dto.LocalityDTO;
import com.gestionvaccination.userservice.config.FeignClientConfig;
import com.gestionvaccination.userservice.dto.AuthRequestDTO;
import com.gestionvaccination.userservice.enumeration.UserRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.gestionvaccination.userservice.client.dto.LocationDTO;

import java.util.List;

/**
 * Client REST pour communiquer avec le Location Service
 */
@FeignClient(
        name = "AUTH-SERVICE"
)
public interface AuthServiceClient {

    /**
     * Récupérer une localité par son ID
     */
    @GetMapping("/api/v1/auth/Infirmier")
    Void SaveInfirmier(@RequestBody AuthRequestDTO authRequestDTO);




}
