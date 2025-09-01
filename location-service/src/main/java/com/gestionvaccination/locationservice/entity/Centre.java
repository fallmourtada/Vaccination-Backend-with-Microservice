package com.gestionvaccination.locationservice.entity;


import com.gestionvaccination.locationservice.enumeration.CentreType;
import com.gestionvaccination.locationservice.helper.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Centre extends AbstractEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private CentreType type;

    @ManyToOne(cascade = CascadeType.ALL)
    private Locality locality;


    @ManyToOne(cascade = CascadeType.ALL)
    private Centre parent;

    private String phone;


}
