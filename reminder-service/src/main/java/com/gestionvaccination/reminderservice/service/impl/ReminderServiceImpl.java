package com.gestionvaccination.reminderservice.service.impl;

import com.gestionvaccination.reminderservice.dto.ReminderDTO;
import com.gestionvaccination.reminderservice.dto.SaveReminderDTO;
import com.gestionvaccination.reminderservice.dto.UpdateReminderDTO;
import com.gestionvaccination.reminderservice.entity.Reminder;
import com.gestionvaccination.reminderservice.exception.ResourceNotFoundException;
import com.gestionvaccination.reminderservice.mapper.ReminderMapper;
import com.gestionvaccination.reminderservice.repository.ReminderRepository;
import com.gestionvaccination.reminderservice.service.EntityEnrichmentService;
import com.gestionvaccination.reminderservice.service.ReminderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository repository;
    private final ReminderMapper mapper;
    private final EntityEnrichmentService enrichmentService;

    public ReminderServiceImpl(ReminderRepository repository,
                               ReminderMapper mapper,
                               EntityEnrichmentService enrichmentService) {
        this.repository = repository;
        this.mapper = mapper;
        this.enrichmentService = enrichmentService;
    }

    @Override
    public ReminderDTO createReminder(SaveReminderDTO saveDto) {
        Reminder entity = mapper.toEntity(saveDto);
        entity = repository.save(entity);
        enrichmentService.enrichReminderWithEnfantData(entity);
        return mapper.toDto(entity);
    }

    @Override
    public ReminderDTO getReminderById(Long id) {
        Reminder entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rappel non trouvé avec l'ID " + id));
        enrichmentService.enrichReminderWithEnfantData(entity);
        return mapper.toDto(entity);
    }

    @Override
    public List<ReminderDTO> getRemindersByEnfant(Long enfantId) {
        List<Reminder> list = repository.findByEnfantId(enfantId);
        enrichmentService.enrichRemindersWithEnfantData(list);
        return mapper.toDtoList(list);
    }

    @Override
    public List<ReminderDTO> getAllReminders() {
        List<Reminder> list = repository.findAll();
        enrichmentService.enrichRemindersWithEnfantData(list);
        return mapper.toDtoList(list);
    }

    @Override
    public ReminderDTO updateReminder(Long id, UpdateReminderDTO updateDto) {
        Reminder entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rappel non trouvé avec l'ID " + id));
        mapper.updateEntity(updateDto, entity);
        entity = repository.save(entity);
        enrichmentService.enrichReminderWithEnfantData(entity);
        return mapper.toDto(entity);
    }

    @Override
    public void deleteReminder(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Rappel non trouvé avec l'ID " + id);
        }
        repository.deleteById(id);
    }
}
