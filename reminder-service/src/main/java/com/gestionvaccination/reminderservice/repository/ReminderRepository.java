package com.gestionvaccination.reminderservice.repository;

import com.gestionvaccination.reminderservice.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository JPA pour les rappels
 */
@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByEnfantId(Long enfantId);
    List<Reminder> findByDateBefore(LocalDate date);
    List<Reminder> findByDateAfter(LocalDate date);
}
