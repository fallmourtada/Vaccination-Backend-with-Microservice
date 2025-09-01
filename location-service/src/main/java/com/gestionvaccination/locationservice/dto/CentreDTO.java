package com.gestionvaccination.locationservice.dto;

import com.gestionvaccination.locationservice.enumeration.CentreType;
import com.gestionvaccination.locationservice.enumeration.LocalityType;
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
