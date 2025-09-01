package com.gestionvaccination.vaccinationservice.client.dto;

import lombok.Data;

@Data
public class CentreDTO {
    private Long id;

    private String name;

    private String address;

    private String phone;

    private LocationDTO locality;


}
