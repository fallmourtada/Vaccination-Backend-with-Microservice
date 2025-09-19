package com.gestionvaccination.auth_service.service;

import com.gestionvaccination.auth_service.dto.AuthRequestDTO;
import com.gestionvaccination.auth_service.enumeration.UserRole;

public interface AuthService {

    public void saveUser(AuthRequestDTO authRequestDTO);
}
