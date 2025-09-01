package com.gestionvaccination.reportingservice.client.rest;

import com.gestionvaccination.reportingservice.dto.ReportEntryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Feign client untuk récupérer les statistiques d'utilisation des vaccins
 */
@FeignClient(name = "vaccine-service")
public interface VaccineServiceClient {

    @GetMapping("/api/vaccines/usage-stats")
    List<ReportEntryDTO> getVaccineUsageStats();
}
