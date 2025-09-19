package com.gestionvaccination.userservice.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class SaveInfirmierDTO {
    private String nom;

    private String prenom;

    private String email;

    private String password;

    private String phone;

    private String matricule;

    private LocalDate dateEmbauche;

    //private Long centreId;

    private Long age;
}
