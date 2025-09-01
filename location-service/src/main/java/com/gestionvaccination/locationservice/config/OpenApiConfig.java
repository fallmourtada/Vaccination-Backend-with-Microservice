package com.gestionvaccination.locationservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:9090");
        localServer.setDescription("Serveur local");

        Contact contact = new Contact();
        contact.setName("Chift Project Team");
        contact.setEmail("contact@chiftproject.com");
        contact.setUrl("https://www.chiftproject.com");

        License license = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0");

        Info info = new Info()
                .title("API de Service de Localité")
                .version("1.0")
                .description("Cette API gère les opérations CRUD des localités (pays, régions, villes, etc.)")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}