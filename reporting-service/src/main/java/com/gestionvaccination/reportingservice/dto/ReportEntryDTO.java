package com.gestionvaccination.reportingservice.dto;

/**
 * DTO générique pour représenter une entrée de rapport
 */
public class ReportEntryDTO {
    private String label;
    private Long value;

    public ReportEntryDTO() {}
    public ReportEntryDTO(String label, Long value) {
        this.label = label;
        this.value = value;
    }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public Long getValue() { return value; }
    public void setValue(Long value) { this.value = value; }
}
