package com.gestionvaccination.reminderservice.dto;

import com.gestionvaccination.reminderservice.client.dto.EnfantDTO;
import java.time.LocalDate;

/**
 * DTO pour représenter un rappel
 */
public class ReminderDTO {
    private Long id;
    private Long enfantId;
    private LocalDate date;
    private String message;
    private EnfantDTO enfant;

    // getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEnfantId() { return enfantId; }
    public void setEnfantId(Long enfantId) { this.enfantId = enfantId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public EnfantDTO getEnfant() { return enfant; }
    public void setEnfant(EnfantDTO enfant) { this.enfant = enfant; }
}
