package com.gestionvaccination.appointmentservice.repository;

import com.gestionvaccination.appointmentservice.entity.Appointment;
import com.gestionvaccination.appointmentservice.enumeration.StatutRv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByEnfantId(Long enfantId);

    List<Appointment> findByUserId(Long userId);

    // Méthode pour trouver les rendez-vous par date et statut
    List<Appointment> findByDateAndStatut(LocalDate date, StatutRv statut);
}
