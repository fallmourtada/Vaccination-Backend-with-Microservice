package com.gestionvaccination.appointmentservice.client.dto;

import com.gestionvaccination.appointmentservice.client.enumeration.CentreType;
import lombok.Data;

import java.util.Date;

@Data
public class CentreDTO {
    private Long id;

    private String name;

    private CentreType type;

    private LocalityDTO locality;

    private CentreDTO parent;

    private String phone;

    private Date createdAt;

    private Date updatedAt;

}
