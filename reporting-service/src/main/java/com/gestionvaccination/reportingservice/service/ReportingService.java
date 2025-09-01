package com.gestionvaccination.reportingservice.service;

import com.gestionvaccination.reportingservice.dto.ReportEntryDTO;
import com.gestionvaccination.reportingservice.enumeration.ReportType;

import java.util.List;

public interface ReportingService {
    List<ReportEntryDTO> generateReport(ReportType type);
}
