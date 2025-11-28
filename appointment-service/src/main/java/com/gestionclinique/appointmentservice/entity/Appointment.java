package com.gestionclinique.appointmentservice.entity;

import com.gestionclinique.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionclinique.appointmentservice.enumeration.StatutRv;
import com.gestionclinique.appointmentservice.helper.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointments")
public class Appointment extends AbstractEntity {

    private Long patientId;


    private Long infirmierId;




    @Enumerated(EnumType.STRING)
    private StatutRv statut;


    @Column(nullable = false)
    private LocalDate date;


    @Column(nullable = false)
    private LocalTime time;


    @Transient
    private UtilisateurDTO infirmier;

    @Transient
    private UtilisateurDTO patient;




}
