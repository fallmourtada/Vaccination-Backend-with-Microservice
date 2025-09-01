package com.gestionvaccination.vaccineservice.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gestionvaccination.vaccineservice.dto.SaveVaccineDTO;
import com.gestionvaccination.vaccineservice.dto.UpdateVaccineDTO;
import com.gestionvaccination.vaccineservice.dto.VaccineDTO;
import com.gestionvaccination.vaccineservice.enumeration.VaccineType;
import com.gestionvaccination.vaccineservice.service.VaccineService;

@WebMvcTest(VaccineController.class)
public class VaccineControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private VaccineService vaccineService;
    
    private ObjectMapper objectMapper;
    private VaccineDTO vaccineDTO;
    private SaveVaccineDTO saveVaccineDTO;
    private UpdateVaccineDTO updateVaccineDTO;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        // Configuration des objets de test
        vaccineDTO = new VaccineDTO();
        vaccineDTO.setId(1L);
        vaccineDTO.setNom("VaccinTest");
        vaccineDTO.setNumeroLot("LOT123");
        vaccineDTO.setTypeVaccin(VaccineType.COVID19);
        vaccineDTO.setDateExpiration(LocalDate.now().plusMonths(6));
        vaccineDTO.setQuantiteDisponible(100);
        
        saveVaccineDTO = new SaveVaccineDTO();
        saveVaccineDTO.setNom("VaccinTest");
        saveVaccineDTO.setNumeroLot("LOT123");
        saveVaccineDTO.setTypeVaccin(VaccineType.COVID19);
        saveVaccineDTO.setDateExpiration(LocalDate.now().plusMonths(6));
        saveVaccineDTO.setQuantiteDisponible(100);
        
        updateVaccineDTO = new UpdateVaccineDTO();
        updateVaccineDTO.setNom("VaccinTestUpdated");
        updateVaccineDTO.setQuantiteDisponible(200);
    }
    
    @Test
    void testCreerVaccin() throws Exception {
        when(vaccineService.creerVaccin(any(SaveVaccineDTO.class))).thenReturn(vaccineDTO);
        
        mockMvc.perform(post("/api/vaccines")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saveVaccineDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("VaccinTest"))
                .andExpect(jsonPath("$.numeroLot").value("LOT123"));
        
        verify(vaccineService).creerVaccin(any(SaveVaccineDTO.class));
    }
    
    @Test
    void testObtenirVaccinParId() throws Exception {
        when(vaccineService.obtenirVaccinParId(anyLong())).thenReturn(vaccineDTO);
        
        mockMvc.perform(get("/api/vaccines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("VaccinTest"))
                .andExpect(jsonPath("$.numeroLot").value("LOT123"));
        
        verify(vaccineService).obtenirVaccinParId(1L);
    }
    
    @Test
    void testMettreAJourVaccin() throws Exception {
        // Créer un VaccineDTO modifié pour le retour
        VaccineDTO updatedVaccineDTO = new VaccineDTO();
        updatedVaccineDTO.setId(1L);
        updatedVaccineDTO.setNom("VaccinTestUpdated");
        updatedVaccineDTO.setNumeroLot("LOT123");
        updatedVaccineDTO.setQuantiteDisponible(200);
        
        when(vaccineService.mettreAJourVaccin(anyLong(), any(UpdateVaccineDTO.class))).thenReturn(updatedVaccineDTO);
        
        mockMvc.perform(put("/api/vaccines/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateVaccineDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("VaccinTestUpdated"))
                .andExpect(jsonPath("$.quantiteDisponible").value(200));
        
        verify(vaccineService).mettreAJourVaccin(eq(1L), any(UpdateVaccineDTO.class));
    }
    
    @Test
    void testSupprimerVaccin() throws Exception {
        doNothing().when(vaccineService).supprimerVaccin(anyLong());
        
        mockMvc.perform(delete("/api/vaccines/1"))
                .andExpect(status().isNoContent());
        
        verify(vaccineService).supprimerVaccin(1L);
    }
    
    @Test
    void testObtenirTousLesVaccins() throws Exception {
        List<VaccineDTO> vaccineDTOs = new ArrayList<>();
        vaccineDTOs.add(vaccineDTO);
        Page<VaccineDTO> page = new PageImpl<>(vaccineDTOs);
        
        when(vaccineService.obtenirTousLesVaccins(any(Pageable.class))).thenReturn(page);
        
        mockMvc.perform(get("/api/vaccines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].nom").value("VaccinTest"));
        
        verify(vaccineService).obtenirTousLesVaccins(any(Pageable.class));
    }
    
    @Test
    void testRechercherVaccins() throws Exception {
        List<VaccineDTO> vaccineDTOs = new ArrayList<>();
        vaccineDTOs.add(vaccineDTO);
        Page<VaccineDTO> page = new PageImpl<>(vaccineDTOs);
        
        when(vaccineService.rechercherVaccins(anyString(), anyString(), any(), any(), any(Pageable.class))).thenReturn(page);
        
        mockMvc.perform(get("/api/vaccines/search")
                .param("nom", "Vaccin")
                .param("fabricant", "Pfizer")
                .param("typeVaccin", "COVID19")
                .param("available", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].nom").value("VaccinTest"));
        
        verify(vaccineService).rechercherVaccins(eq("Vaccin"), eq("Pfizer"), eq(VaccineType.COVID19), eq(true), any(Pageable.class));
    }
    
    @Test
    void testMettreAJourStock() throws Exception {
        // Créer un VaccineDTO avec stock mis à jour
        VaccineDTO stockUpdatedVaccineDTO = new VaccineDTO();
        stockUpdatedVaccineDTO.setId(1L);
        stockUpdatedVaccineDTO.setNom("VaccinTest");
        stockUpdatedVaccineDTO.setQuantiteDisponible(150);
        
        when(vaccineService.mettreAJourStock(anyLong(), anyInt())).thenReturn(stockUpdatedVaccineDTO);
        
        mockMvc.perform(patch("/api/vaccines/1/stock")
                .param("quantiteAjoutee", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantiteDisponible").value(150));
        
        verify(vaccineService).mettreAJourStock(eq(1L), eq(50));
    }
}
