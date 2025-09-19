package com.gestionvaccination.auth_service.security;
import com.gestionvaccination.auth_service.client.UserServiceClient;
import com.gestionvaccination.auth_service.dto.UtilisateurDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@CrossOrigin("*")

public class SecurityController {
    private AuthenticationManager authenticationManager;
    private UserServiceClient userServiceClient;

   // private UserRepository userRepository;

    private JwtEncoder jwtEncoder;

    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @PostMapping("/login")
    public Map<String ,String> login(String username, String password){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );
        Instant instant=Instant.now();
        String scope=authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(""));
        //User user  = userRepository.findUserByEmail(username);
        // Si le user n'est pas trouvé, lancer une exception
//         if (user == null) {
//           throw new RuntimeException("Utilisateur introuvable");
//         }

        UtilisateurDTO user = userServiceClient.getUserByEmail(username);
        if (user == null) {
          throw new RuntimeException("Utilisateur introuvable");
        }

        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope",scope)
                .claim("username",user.getEmail())
                .claim("prenom",user.getPrenom())
                .claim("nom",user.getNom())
                .claim("telephone",user.getTelephone())
                .claim("id",user.getId())
                .claim("Age",user.getAge())
                //.claim("Adresse",user.getAdresse())
                //.claim("profession",user.getProfession())
                //.claim("niveauDetude",user.getNiveauEtude())
                //.claim("idUser", user != null ? user.getId() : null) // ✅ Ajouter idEtudiant
                .build();
        JwtEncoderParameters jwtEncoderParameters=
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );
        String jwt=jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        return Map.of("access-token",jwt);
    }

}