package com.gestionvaccination.vaccinationservice.client.rest;

import com.gestionvaccination.vaccinationservice.client.dto.VaccineDTO;
import com.gestionvaccination.vaccinationservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vaccin-service")
public interface VaccinServiceClient {

    @GetMapping("/api/v1/vaccines/{vaccinId}")
    VaccineDTO getVaccinById(@PathVariable Long vaccinId);

}
