package com.gestionvaccination.auth_service1.client.dto;

import java.util.Date;


import com.gestionvaccination.auth_service1.client.enums.CentreType;
import lombok.Data;

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
