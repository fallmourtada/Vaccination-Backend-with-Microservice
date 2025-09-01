package com.gestionvaccination.appointmentservice.client.rest;

import com.gestionvaccination.appointmentservice.client.dto.EnfantDTO;
import com.gestionvaccination.appointmentservice.client.dto.UtilisateurDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/{userId}")
    UtilisateurDTO getUserById(@PathVariable Long userId);



    @GetMapping("/api/v1/users/enfants/{enfantId}")
    EnfantDTO getEnfantById(@PathVariable Long enfantId);
}
