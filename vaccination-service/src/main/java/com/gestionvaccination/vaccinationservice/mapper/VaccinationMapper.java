package com.gestionvaccination.vaccinationservice.mapper;

import com.gestionvaccination.vaccinationservice.client.dto.*;
import com.gestionvaccination.vaccinationservice.enumeration.StatutVaccination;
import org.springframework.stereotype.Component;
import com.gestionvaccination.vaccinationservice.entity.Vaccination;
import com.gestionvaccination.vaccinationservice.dto.VaccinationDTO;
import com.gestionvaccination.vaccinationservice.dto.SaveVaccinationDTO;
import com.gestionvaccination.vaccinationservice.dto.UpdateVaccinationDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir entre Vaccination et ses DTOs
 */
@Service
public class VaccinationMapper {
    public VaccinationDTO fromEntity(Vaccination vaccination) {
        if(vaccination == null){
            return null;
        }

        VaccinationDTO vaccinationDTO = new VaccinationDTO();
        vaccinationDTO.setId(vaccination.getId());
        vaccinationDTO.setStatutVaccination(vaccination.getStatutVaccination());
        vaccinationDTO.setDate(vaccination.getDate());


        if(vaccination.getVaccine()!= null){
            vaccinationDTO.setVaccine(vaccination.getVaccine());
        }


        if(vaccination.getUtilisateur()!= null){
            vaccinationDTO.setUtilisateur(vaccination.getUtilisateur());
        }

       if(vaccination.getAppointment()!= null){
           vaccinationDTO.setAppointment(vaccination.getAppointment());
       }

        return vaccinationDTO;
    }


    public Vaccination partialUpdate(UpdateVaccinationDTO updateVaccinationDTO, Vaccination vaccination) {
        if(updateVaccinationDTO == null || vaccination == null){
            return null;
        }

        if(updateVaccinationDTO.getStatutVaccination()!= null){
            vaccination.setStatutVaccination(updateVaccinationDTO.getStatutVaccination());
        }



      if(updateVaccinationDTO.getDate()!= null){
          vaccination.setDate(updateVaccinationDTO.getDate());
      }

        return vaccination;
    }



    public Vaccination fromSaveVaccination(SaveVaccinationDTO saveVaccinationDTO,
                                           UtilisateurDTO user,
                                           AppointmentDTO appointment,
                                           VaccineDTO vaccine,
                                           EnfantDTO enfant) {

        Vaccination vaccination = new Vaccination();
        vaccination.setStatutVaccination(StatutVaccination.EFFECTUER);
        vaccination.setDate(LocalDate.now());





        if(user != null){
            vaccination.setUserId(user.getId());
            vaccination.setUtilisateur(user);
        }else {
            vaccination.setUserId(null);
        }


        if(appointment != null){
            vaccination.setAppointmentId(appointment.getId());
            vaccination.setAppointment(appointment);
        }else {
            vaccination.setAppointmentId(null);
            vaccination.setAppointment(null);
        }


        if(vaccine != null){
            vaccination.setVaccineId(vaccine.getId());
            vaccination.setVaccine(vaccine);
        }else {
            vaccination.setVaccineId(null);
            vaccination.setVaccine(null);
        }

        if(enfant != null){
            vaccination.setEnfantId(enfant.getId());
            vaccination.setEnfant(enfant);
        }else {
            vaccination.setEnfantId(null);
            vaccination.setEnfant(null);
        }

        return vaccination;

    }

}
