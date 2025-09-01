package com.gestionvaccination.reminderservice.service;

import com.gestionvaccination.reminderservice.entity.Reminder;
import java.util.List;

/**
 * Interface pour le service d'enrichissement des entités Reminder
 */
public interface EntityEnrichmentService {

    /**
     * Enrichir un rappel avec les données de l'enfant
     */
    void enrichReminderWithEnfantData(Reminder reminder);

    /**
     * Enrichir une liste de rappels
     */
    void enrichRemindersWithEnfantData(List<Reminder> reminders);
}
