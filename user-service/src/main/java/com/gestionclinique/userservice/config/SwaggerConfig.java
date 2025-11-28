package com.gestionclinique.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "User Service API",
                version = "1.0",
                description = "Documentation de l'API User Service"
        ),
        security = {
                @SecurityRequirement(name = "keycloakAuth")
        }
)
@SecurityScheme(
        name = "keycloakAuth",
        type = SecuritySchemeType.OAUTH2,
        flows = @io.swagger.v3.oas.annotations.security.OAuthFlows(
                authorizationCode = @io.swagger.v3.oas.annotations.security.OAuthFlow(
                        authorizationUrl = "http://localhost:8080/realms/chift-realm/protocol/openid-connect/auth",
                        tokenUrl = "http://localhost:8080/realms/chift-realm/protocol/openid-connect/token"
                )
        )
)
public class SwaggerConfig {
}
