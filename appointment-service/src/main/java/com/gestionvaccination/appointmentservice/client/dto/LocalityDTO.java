package com.gestionvaccination.appointmentservice.client.dto;

import com.gestionvaccination.appointmentservice.client.enumeration.LocalityType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class LocalityDTO {

    private Long id;

    private String name;

    private String codification;


    @Enumerated(EnumType.STRING)
    private LocalityType type;

    Date createdAt;

    Date updatedAt;


    private LocalityDTO  parent;
}
