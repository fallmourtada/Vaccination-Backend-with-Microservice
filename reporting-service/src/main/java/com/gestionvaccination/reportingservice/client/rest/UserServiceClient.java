package com.gestionvaccination.reportingservice.client.rest;

import com.gestionvaccination.reportingservice.dto.ReportEntryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    /**
     * Appelle l'API du user-service pour obtenir les statistiques des enfants par localité
     */
    @GetMapping("/api/enfants/statistiques/localites")
    List<ReportEntryDTO> getEnfantsStatsByLocalite();
}
