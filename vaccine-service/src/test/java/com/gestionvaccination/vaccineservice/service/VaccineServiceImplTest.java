package com.gestionvaccination.vaccineservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gestionvaccination.vaccineservice.dto.SaveVaccineDTO;
import com.gestionvaccination.vaccineservice.dto.VaccineDTO;
import com.gestionvaccination.vaccineservice.entity.Vaccine;
import com.gestionvaccination.vaccineservice.enumeration.VaccineType;
import com.gestionvaccination.vaccineservice.exception.ResourceNotFoundException;
import com.gestionvaccination.vaccineservice.mapper.VaccineMapper;
import com.gestionvaccination.vaccineservice.repository.VaccineRepository;
import com.gestionvaccination.vaccineservice.service.impl.VaccineServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class VaccineServiceImplTest {

    @Mock
    private VaccineRepository vaccineRepository;
    
    @Mock
    private VaccineMapper vaccineMapper;
    
    @Mock
    private EntityEnrichmentService entityEnrichmentService;
    
    @InjectMocks
    private VaccineServiceImpl vaccineService;
    
    private Vaccine vaccine;
    private VaccineDTO vaccineDTO;
    private SaveVaccineDTO saveVaccineDTO;
    
    @BeforeEach
    void setUp() {
        // Configuration des objets de test
        vaccine = new Vaccine();
        vaccine.setId(1L);
        vaccine.setNom("VaccinTest");
        vaccine.setNumeroLot("LOT123");
        vaccine.setTypeVaccin(VaccineType.COVID19);
        vaccine.setDateExpiration(LocalDate.now().plusMonths(6));
        vaccine.setQuantiteDisponible(100);
        vaccine.setLocalityId(1L);
        
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
        saveVaccineDTO.setLocalityId(1L);
    }
    
    @Test
    void testCreerVaccin() {
        // Given
        when(vaccineRepository.existsByNom(anyString())).thenReturn(false);
        when(vaccineRepository.existsByNumeroLot(anyString())).thenReturn(false);
        when(vaccineMapper.toEntity(any(SaveVaccineDTO.class))).thenReturn(vaccine);
        when(vaccineRepository.save(any(Vaccine.class))).thenReturn(vaccine);
        when(vaccineMapper.toDto(any(Vaccine.class))).thenReturn(vaccineDTO);
        doNothing().when(entityEnrichmentService).enrichVaccineWithLocationData(any(Vaccine.class));
        
        // When
        VaccineDTO result = vaccineService.creerVaccin(saveVaccineDTO);
        
        // Then
        assertNotNull(result);
        assertEquals("VaccinTest", result.getNom());
        assertEquals("LOT123", result.getNumeroLot());
        assertEquals(VaccineType.COVID19, result.getTypeVaccin());
        
        // Verify
        verify(vaccineRepository).existsByNom(anyString());
        verify(vaccineRepository).existsByNumeroLot(anyString());
        verify(vaccineMapper).toEntity(any(SaveVaccineDTO.class));
        verify(vaccineRepository).save(any(Vaccine.class));
        verify(entityEnrichmentService).enrichVaccineWithLocationData(any(Vaccine.class));
        verify(vaccineMapper).toDto(any(Vaccine.class));
    }
    
    @Test
    void testCreerVaccinNomExistant() {
        // Given
        when(vaccineRepository.existsByNom(anyString())).thenReturn(true);
        
        // When & Then
        assertThrows(ResponseStatusException.class, () -> {
            vaccineService.creerVaccin(saveVaccineDTO);
        });
        
        // Verify
        verify(vaccineRepository).existsByNom(anyString());
        verify(vaccineRepository, never()).save(any(Vaccine.class));
    }
    
    @Test
    void testObtenirVaccinParId() {
        // Given
        when(vaccineRepository.findById(anyLong())).thenReturn(Optional.of(vaccine));
        when(vaccineMapper.toDto(any(Vaccine.class))).thenReturn(vaccineDTO);
        doNothing().when(entityEnrichmentService).enrichVaccineWithLocationData(any(Vaccine.class));
        
        // When
        VaccineDTO result = vaccineService.obtenirVaccinParId(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("VaccinTest", result.getNom());
        
        // Verify
        verify(vaccineRepository).findById(anyLong());
        verify(entityEnrichmentService).enrichVaccineWithLocationData(any(Vaccine.class));
        verify(vaccineMapper).toDto(any(Vaccine.class));
    }
    
    @Test
    void testObtenirVaccinParIdNonExistant() {
        // Given
        when(vaccineRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            vaccineService.obtenirVaccinParId(1L);
        });
        
        // Verify
        verify(vaccineRepository).findById(anyLong());
        verify(entityEnrichmentService, never()).enrichVaccineWithLocationData(any(Vaccine.class));
        verify(vaccineMapper, never()).toDto(any(Vaccine.class));
    }
    
    @Test
    void testObtenirVaccinParType() {
        // Given
        List<Vaccine> vaccines = new ArrayList<>();
        vaccines.add(vaccine);
        
        List<VaccineDTO> vaccineDTOs = new ArrayList<>();
        vaccineDTOs.add(vaccineDTO);
        
        when(vaccineRepository.trouverParTypeVaccin(any(VaccineType.class))).thenReturn(vaccines);
        when(vaccineMapper.toDtoList(anyList())).thenReturn(vaccineDTOs);
        doNothing().when(entityEnrichmentService).enrichVaccinesWithLocationData(anyList());
        
        // When
        List<VaccineDTO> results = vaccineService.obtenirVaccinParType(VaccineType.COVID19);
        
        // Then
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("VaccinTest", results.get(0).getNom());
        
        // Verify
        verify(vaccineRepository).trouverParTypeVaccin(any(VaccineType.class));
        verify(entityEnrichmentService).enrichVaccinesWithLocationData(anyList());
        verify(vaccineMapper).toDtoList(anyList());
    }
    
    @Test
    void testMettreAJourStock() {
        // Given
        when(vaccineRepository.findById(anyLong())).thenReturn(Optional.of(vaccine));
        when(vaccineRepository.save(any(Vaccine.class))).thenReturn(vaccine);
        when(vaccineMapper.toDto(any(Vaccine.class))).thenReturn(vaccineDTO);
        doNothing().when(entityEnrichmentService).enrichVaccineWithLocationData(any(Vaccine.class));
        
        // When
        VaccineDTO result = vaccineService.mettreAJourStock(1L, 50);
        
        // Then
        assertNotNull(result);
        
        // Verify
        verify(vaccineRepository).findById(anyLong());
        verify(vaccineRepository).save(any(Vaccine.class));
        verify(entityEnrichmentService).enrichVaccineWithLocationData(any(Vaccine.class));
        verify(vaccineMapper).toDto(any(Vaccine.class));
    }
    
    @Test
    void testMettreAJourStockQuantiteNegative() {
        // Given
        when(vaccineRepository.findById(anyLong())).thenReturn(Optional.of(vaccine));
        
        // When & Then
        assertThrows(ResponseStatusException.class, () -> {
            vaccineService.mettreAJourStock(1L, -150); // Quantité actuelle 100, ajout de -150 = -50
        });
        
        // Verify
        verify(vaccineRepository).findById(anyLong());
        verify(vaccineRepository, never()).save(any(Vaccine.class));
    }
}
