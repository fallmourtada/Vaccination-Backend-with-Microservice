package com.gestionvaccination.auth_service1.client.dto;

import java.util.Date;


import com.gestionvaccination.auth_service1.client.enums.LocalityType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
