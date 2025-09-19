package com.gestionvaccination.userservice.client.rest;

import com.gestionvaccination.userservice.client.dto.VaccinationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "VACCINATION-SERVICE", contextId = "vaccinationClient")
public interface VaccinationServiceClient {

    @GetMapping("/api/v1/vaccinations/byQrCode/{qrCode}")
    List<VaccinationDTO> getVaccinationsByQrCode(@PathVariable String qrCode);

    @GetMapping("/api/v1/vaccinations/enfant/{enfantId}")
    List<VaccinationDTO> getVaccinationsByEnfant(@PathVariable Long enfantId);
}
