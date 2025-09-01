package com.gestionvaccination.appointmentservice.dto;

import com.gestionvaccination.appointmentservice.client.dto.LocalityDTO;
import lombok.Data;

@Data
public class CentreDTO {
    private Long id;

    private String name;

    private String address;

    private String phone;

    private LocalityDTO locality;


}
