package com.gestionvaccination.reminderservice.client.rest;

import com.gestionvaccination.reminderservice.client.dto.EnfantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client pour communiquer avec le user-service
 */
@FeignClient(name = "user-service")
public interface EnfantServiceClient {

    @GetMapping("/api/enfants/{id}")
    EnfantDTO getEnfantById(@PathVariable("id") Long id);
}
