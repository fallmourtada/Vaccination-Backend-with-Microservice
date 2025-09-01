package com.gestionvaccination.reportingservice.service.impl;

import com.gestionvaccination.reportingservice.client.rest.AppointmentServiceClient;
import com.gestionvaccination.reportingservice.client.rest.UserServiceClient;
import com.gestionvaccination.reportingservice.client.rest.VaccineServiceClient;
import com.gestionvaccination.reportingservice.dto.ReportEntryDTO;
import com.gestionvaccination.reportingservice.enumeration.ReportType;
import com.gestionvaccination.reportingservice.service.ReportingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final UserServiceClient userServiceClient;
    private final VaccineServiceClient vaccineServiceClient;
    private final AppointmentServiceClient appointmentServiceClient;

    public ReportingServiceImpl(UserServiceClient userServiceClient,
                                VaccineServiceClient vaccineServiceClient,
                                AppointmentServiceClient appointmentServiceClient) {
        this.userServiceClient = userServiceClient;
        this.vaccineServiceClient = vaccineServiceClient;
        this.appointmentServiceClient = appointmentServiceClient;
    }

    @Override
    public List<ReportEntryDTO> generateReport(ReportType type) {
        switch (type) {
            case ENFANTS_PAR_LOCALITE:
                return userServiceClient.getEnfantsStatsByLocalite();
            case VACCINS_UTILISES:
                return vaccineServiceClient.getVaccineUsageStats();
            case RENDEZVOUS_PAR_LOCALITE:
                return appointmentServiceClient.getAppointmentsStatsByLocalite();
            case STAT_VACCINATION:
                List<ReportEntryDTO> combined = new ArrayList<>();
                combined.addAll(userServiceClient.getEnfantsStatsByLocalite());
                combined.addAll(vaccineServiceClient.getVaccineUsageStats());
                combined.addAll(appointmentServiceClient.getAppointmentsStatsByLocalite());
                return combined;
            default:
                throw new IllegalArgumentException("Type de rapport non géré: " + type);
        }
    }
}
