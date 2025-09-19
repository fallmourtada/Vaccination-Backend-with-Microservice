package com.gestionvaccination.vaccinationservice.client.rest;


import com.gestionvaccination.vaccinationservice.client.dto.AppointmentDTO;
import com.gestionvaccination.vaccinationservice.client.dto.UpdateStatutAppointmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "appointment-service")
public interface AppointmentServiceClient {


    @GetMapping("/api/v1/appointments/{appointmentId}")
    AppointmentDTO getAppointmentById(@PathVariable Long appointmentId);


    @PutMapping("/api/v1/appointments/{appointmentId}/status")
    void updateStatuAppointment(@PathVariable Long appointmentId, @RequestBody UpdateStatutAppointmentDTO updateStatutAppointmentDTO);
}
