package com.gestionvaccination.reminderservice.entity;

import com.gestionvaccination.reminderservice.client.dto.EnfantDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long enfantId;
    private LocalDate date;
    private String message;

    @Transient
    private EnfantDTO enfant;
}
