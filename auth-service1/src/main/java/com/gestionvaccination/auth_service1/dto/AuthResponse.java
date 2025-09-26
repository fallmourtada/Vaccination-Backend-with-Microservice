package com.gestionvaccination.auth_service1.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String type;
}
