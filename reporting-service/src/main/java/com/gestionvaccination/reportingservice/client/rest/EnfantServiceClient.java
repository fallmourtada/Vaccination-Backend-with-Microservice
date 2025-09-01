package com.gestionvaccination.reportingservice.client.rest;

import com.gestionvaccination.reportingservice.client.dto.EnfantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface EnfantServiceClient {
    @GetMapping("/api/enfants/localite/{localiteId}")
    List<ReportEntryDTO> getChildrenByLocality(@PathVariable("localiteId") Long localiteId);
}
