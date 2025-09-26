package com.gestionvaccination.userservice.servicesImpl;

import com.gestionvaccination.userservice.dto.EnfantDTO;
import com.gestionvaccination.userservice.entites.Enfant;
import com.gestionvaccination.userservice.entites.Utilisateur;
import com.gestionvaccination.userservice.mapper.EnfantMapper;
import com.gestionvaccination.userservice.repository.UtilisateurRepository;
import com.gestionvaccination.userservice.services.UserStatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserStatsServiceImpl implements UserStatsService {

    private final UtilisateurRepository utilisateurRepository;
    private final EnfantMapper enfantMapper;

    @Override
    public List<EnfantDTO> getEnfantsByCentre(Long centreId) {
        List<Utilisateur> parents = utilisateurRepository.findByCentreId(centreId);

        return parents.stream()
                .flatMap(parent -> parent.getEnfants().stream())
                .map(enfantMapper::fromEntity) // Utilisez une méthode de conversion
                .collect(Collectors.toList());
    }
}
