package com.gestionvaccination.appointmentservice.repository;

import com.gestionvaccination.appointmentservice.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByEnfantId(Long enfantId);

    List<Appointment> findByUserId(Long userId);
}
