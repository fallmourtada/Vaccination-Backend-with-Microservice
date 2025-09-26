package com.gestionvaccination.auth_service1.client.rest;

import com.gestionvaccination.auth_service1.client.dto.UtilisateurDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service", url = "http://localhost:8083") // URL du service User
public interface UserServiceClient {

    @GetMapping("/api/v1/users/findUserByEmail/{email}")
    UtilisateurDTO getUserByEmail(@PathVariable String email);
    
}
