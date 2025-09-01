package com.gestionvaccination.reminderservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

/**
 * DTO pour mettre à jour un rappel existant
 */
public class UpdateReminderDTO {
    private LocalDate date;
    private String message;

    // getters et setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
