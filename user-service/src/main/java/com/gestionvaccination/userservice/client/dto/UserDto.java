package com.gestionvaccination.userservice.client.dto;




import com.gestionvaccination.userservice.enumeration.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private UserRole role;
}
