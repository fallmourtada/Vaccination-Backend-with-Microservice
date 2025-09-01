package com.gestionvaccination.appointmentservice.entity;

import com.gestionvaccination.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionvaccination.appointmentservice.enumeration.StatutRv;
import com.gestionvaccination.appointmentservice.helper.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import com.gestionvaccination.appointmentservice.client.dto.EnfantDTO;
import com.gestionvaccination.appointmentservice.dto.CentreDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointments")
public class Appointment extends AbstractEntity {

    private Long enfantId;

    private Long userId;

    private String nomVaccinAEffectuer;

//    @ManyToOne
//    private Centre centre;

    @Enumerated(EnumType.STRING)
    private StatutRv statut;


//    private boolean estEffectuer;


    private LocalDate date;

    @Transient
    private EnfantDTO enfant;

    @Transient
    private UtilisateurDTO utilisateur;


}
