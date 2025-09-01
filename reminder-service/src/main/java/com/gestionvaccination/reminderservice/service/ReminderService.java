package com.gestionvaccination.reminderservice.service;

import com.gestionvaccination.reminderservice.dto.ReminderDTO;
import com.gestionvaccination.reminderservice.dto.SaveReminderDTO;
import com.gestionvaccination.reminderservice.dto.UpdateReminderDTO;

import java.util.List;

/**
 * Interface pour le service de gestion des rappels
 */
public interface ReminderService {
    ReminderDTO createReminder(SaveReminderDTO saveReminderDTO);
    ReminderDTO getReminderById(Long id);
    List<ReminderDTO> getRemindersByEnfant(Long enfantId);
    List<ReminderDTO> getAllReminders();
    ReminderDTO updateReminder(Long id, UpdateReminderDTO updateReminderDTO);
    void deleteReminder(Long id);
}
