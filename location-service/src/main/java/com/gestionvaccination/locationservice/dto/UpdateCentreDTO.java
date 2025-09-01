package com.gestionvaccination.locationservice.dto;


import com.gestionvaccination.locationservice.enumeration.CentreType;
import lombok.Data;

@Data
public class UpdateCentreDTO {
    private String name;

    private String phone;

    private CentreType type;

}
