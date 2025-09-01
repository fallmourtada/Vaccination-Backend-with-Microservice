package com.gestionvaccination.vaccineservice.entity;

import com.gestionvaccination.vaccineservice.enumeration.PeriodePrise;
import com.gestionvaccination.vaccineservice.helpers.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.gestionvaccination.vaccineservice.enumeration.VaccineType;
import com.gestionvaccination.vaccineservice.enumeration.AdministrationMode;
import com.gestionvaccination.vaccineservice.client.dto.LocationDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entité représentant un vaccin dans le système
 */
@Entity
@Table(name = "vaccines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String nom;
    
    @Column(name = "fabricant")
    private String fabricant;
    
    @Column(name = "numero_lot", nullable = false)
    private String numeroLot;
    
    @Column(name = "date_production")
    private LocalDate dateProduction;
    
    @Column(name = "date_expiration", nullable = false)
    private LocalDate dateExpiration;
    
    @Column(name = "description", length = 1000)
    private String description;
    
//    @Column(name = "dosage")
//    private String dosage;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_vaccin", nullable = false)
    private VaccineType typeVaccin;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "mode_administration")
    private AdministrationMode modeAdministration;
    
    @Column(name = "temperature_conservation")
    private String temperatureConservation;

    
    @Column(name = "effets_secondaires", length = 1000)
    private String effetsSecondaires;


    @Column(name = "doses_requises")
    private Integer dosesRequises;
//
//    @Column(name = "quantite_disponible")
//    private Integer quantiteDisponible;

    @Enumerated(EnumType.STRING)
    private PeriodePrise periode;


}
