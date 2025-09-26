package com.gestionvaccination.auth_service1.entity;


import com.gestionvaccination.auth_service1.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Email(message = "Le nom d'utilisateur doit être une adresse email valide")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit supérieur ou égal à 8 caractères")
    private String password;

    // @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Le rôle est obligatoire")
    private UserRole role;
    // private List<UserRole> roles;

    private boolean enabled = true;
}
