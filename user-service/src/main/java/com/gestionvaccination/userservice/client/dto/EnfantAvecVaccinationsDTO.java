package com.gestionvaccination.userservice.client.dto;



import com.gestionvaccination.userservice.dto.EnfantDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnfantAvecVaccinationsDTO {
    private EnfantDTO enfant;
    private List<VaccinationDTO> vaccinations;
}
