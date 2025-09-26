package com.gestionvaccination.auth_service1.dto;



import com.gestionvaccination.auth_service1.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private UserRole role;
}
