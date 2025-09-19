
package com.gestionvaccination.auth_service.client;


import com.gestionvaccination.auth_service.dto.UtilisateurDTO;
import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/findUserByEmail/{email}")
    UtilisateurDTO  getUserByEmail(@PathVariable String email);
}


