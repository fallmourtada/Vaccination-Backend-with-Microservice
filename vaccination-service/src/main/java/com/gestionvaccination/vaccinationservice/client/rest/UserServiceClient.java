package com.gestionvaccination.vaccinationservice.client.rest;

import com.gestionvaccination.vaccinationservice.client.dto.EnfantDTO;
import com.gestionvaccination.vaccinationservice.client.dto.UtilisateurDTO;
import com.gestionvaccination.vaccinationservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "USER-SERVICE",
        contextId = "userClient",
        configuration = FeignClientConfig.class)
public interface UserServiceClient {

    /**
     * Récupère un utilisateur par son ID
     *
     * @param userId ID de l'utilisateur à récupérer
     * @return DTO de l'utilisateur trouvé
     */
    @GetMapping("/api/v1/users/{userId}")
    UtilisateurDTO getUser(@PathVariable Long userId);

    @GetMapping("/api/v1/users/enfants/{enfantId}")
    EnfantDTO getEnfantById(@PathVariable Long enfantId);


    @GetMapping("/api/v1/users/enfants/by-qr-code/{qrCode}")
    EnfantDTO getEnfantByQrCode(@PathVariable String qrCode);


    /**
     * Récupère La liste des Enfants par le centreId
     *
     * @param centreId ID du centre a recuperer
     * @return DTO des enfants  trouvé
     */

    @GetMapping("/api/v1/users/stats/by-centre/{centreId}/enfants")
    List<EnfantDTO> getEnfantsByCentre(@PathVariable("centreId") Long centreId);



}
