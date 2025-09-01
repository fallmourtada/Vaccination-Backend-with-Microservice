package com.gestionvaccination.reminderservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * DTO pour créer un nouveau rappel
 */
public class SaveReminderDTO {

    @NotNull(message = "L'ID de l'enfant est obligatoire")
    private Long enfantId;

    @NotNull(message = "La date du rappel est obligatoire")
    @FutureOrPresent(message = "La date du rappel doit être aujourd'hui ou dans le futur")
    private LocalDate date;

    @NotBlank(message = "Le message est obligatoire")
    private String message;

    // getters et setters
    public Long getEnfantId() {
        return enfantId;
    }

    public void setEnfantId(Long enfantId) {
        this.enfantId = enfantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
