package com.gestionvaccination.auth_service1.config;


import com.gestionvaccination.auth_service1.entity.User;
import com.gestionvaccination.auth_service1.enums.UserRole;
import com.gestionvaccination.auth_service1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Service d'initialisation pour créer un utilisateur administrateur par défaut
 * au premier démarrage de l'application
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.default-email:admin@vaccination.sn}")
    private String defaultAdminEmail;

    @Value("${app.admin.default-password:Admin123!}")
    private String defaultAdminPassword;

    @Override
    public void run(String... args) throws Exception {
        createDefaultAdminIfNotExists();
    }

    /**
     * Crée un administrateur par défaut si aucun utilisateur ADMIN n'existe
     */
    private void createDefaultAdminIfNotExists() {
        log.info("🔍 Vérification de l'existence d'un administrateur...");

        // Vérifier s'il existe déjà un administrateur
        boolean adminExists = userRepository.existsByRole(UserRole.ADMIN);

        if (!adminExists) {
            log.info("👑 Aucun administrateur trouvé, création de l'administrateur par défaut...");
            
            User adminUser = new User();
            adminUser.setUsername(defaultAdminEmail);
            adminUser.setPassword(passwordEncoder.encode(defaultAdminPassword));
            adminUser.setRole(UserRole.ADMIN);
            adminUser.setEnabled(true);

            try {
                userRepository.save(adminUser);
                log.info("Administrateur par défaut créé avec succès !");
                log.info("Email : {}", defaultAdminEmail);
                log.info("Mot de passe : {}", defaultAdminPassword);
                log.warn("IMPORTANT : Changez le mot de passe par défaut après la première connexion !");
                
                printAdminInfo();
                
            } catch (Exception e) {
                log.error("Erreur lors de la création de l'administrateur par défaut : {}", e.getMessage());
            }
        } else {
            log.info("✅ Un administrateur existe déjà dans le système.");
        }
    }

    /**
     * Affiche les informations de connexion administrateur
     */
    private void printAdminInfo() {
        log.info("════════════════════════════════════════════════════════════════");
        log.info("🎯 INFORMATIONS DE CONNEXION ADMINISTRATEUR");
        log.info("════════════════════════════════════════════════════════════════");
        log.info("📧 Email     : {}", defaultAdminEmail);
        log.info("🔑 Password  : {}", defaultAdminPassword);
        log.info("🌐 Auth URL  : http://localhost:8081/api/v1/auth/login");
        log.info("📊 Swagger   : http://localhost:8081/swagger-ui.html");
        log.info("════════════════════════════════════════════════════════════════");
        log.warn("⚠️  SÉCURITÉ : Changez ce mot de passe immédiatement après connexion !");
        log.info("════════════════════════════════════════════════════════════════");
    }
}