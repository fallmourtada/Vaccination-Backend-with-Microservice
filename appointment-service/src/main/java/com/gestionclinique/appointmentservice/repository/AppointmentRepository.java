package com.gestionclinique.appointmentservice.repository;

import com.gestionclinique.appointmentservice.entity.Appointment;
import com.gestionclinique.appointmentservice.enumeration.StatutRv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

//    List<Appointment> findByUserId(Long userId);

    // Méthode pour trouver les rendez-vous par date et statut
    List<Appointment> findByDateAndStatut(LocalDate date, StatutRv statut);
}
