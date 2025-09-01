package com.gestionvaccination.reminderservice.service.impl;

import com.gestionvaccination.reminderservice.client.rest.EnfantServiceClient;
import com.gestionvaccination.reminderservice.entity.Reminder;
import com.gestionvaccination.reminderservice.service.EntityEnrichmentService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EntityEnrichmentServiceImpl implements EntityEnrichmentService {

    private final EnfantServiceClient enfantServiceClient;

    public EntityEnrichmentServiceImpl(EnfantServiceClient enfantServiceClient) {
        this.enfantServiceClient = enfantServiceClient;
    }

    @Override
    public void enrichReminderWithEnfantData(Reminder reminder) {
        if (reminder.getEnfantId() != null) {
            reminder.setEnfant(enfantServiceClient.getEnfantById(reminder.getEnfantId()));
        }
    }

    @Override
    public void enrichRemindersWithEnfantData(List<Reminder> reminders) {
        reminders.forEach(this::enrichReminderWithEnfantData);
    }
}
