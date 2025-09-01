package com.gestionvaccination.reminderservice.mapper;

import com.gestionvaccination.reminderservice.dto.ReminderDTO;
import com.gestionvaccination.reminderservice.dto.SaveReminderDTO;
import com.gestionvaccination.reminderservice.dto.UpdateReminderDTO;
import com.gestionvaccination.reminderservice.entity.Reminder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir entre Reminder et DTOs
 */
@Component
public class ReminderMapper {

    public ReminderDTO toDto(Reminder reminder) {
        if (reminder == null) return null;
        ReminderDTO dto = new ReminderDTO();
        dto.setId(reminder.getId());
        dto.setEnfantId(reminder.getEnfantId());
        dto.setDate(reminder.getDate());
        dto.setMessage(reminder.getMessage());
        dto.setEnfant(reminder.getEnfant());
        return dto;
    }

    public List<ReminderDTO> toDtoList(List<Reminder> reminders) {
        return reminders.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Reminder toEntity(SaveReminderDTO saveDto) {
        if (saveDto == null) return null;
        Reminder entity = new Reminder();
        entity.setEnfantId(saveDto.getEnfantId());
        entity.setDate(saveDto.getDate());
        entity.setMessage(saveDto.getMessage());
        return entity;
    }

    public void updateEntity(UpdateReminderDTO updateDto, Reminder entity) {
        if (updateDto.getDate() != null) {
            entity.setDate(updateDto.getDate());
        }
        if (updateDto.getMessage() != null) {
            entity.setMessage(updateDto.getMessage());
        }
    }
}
