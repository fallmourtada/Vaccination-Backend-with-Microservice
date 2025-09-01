package com.gestionvaccination.reminderservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration OpenAPI / Swagger
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI reminderServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Reminder Service API")
                        .description("API pour la gestion des rappels de vaccination")
                        .version("1.0"));
    }
}
