package com.gestionvaccination.reportingservice.client.rest;

import com.gestionvaccination.reportingservice.dto.ReportEntryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "appointment-service")
public interface AppointmentServiceClient {
    /**
     * Statistiques des rendez-vous par localité
     */
    @GetMapping("/api/appointments/statistics/localites")
    List<ReportEntryDTO> getAppointmentsStatsByLocalite();
}
