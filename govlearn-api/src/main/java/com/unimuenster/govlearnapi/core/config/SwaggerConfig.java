package com.unimuenster.govlearnapi.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        Server server = new Server();
        server.url("/");

        return new OpenAPI()
                .info(
                        new Info()
                                .title("GovLearn API")
                                .description("Logout via /api/v1/logout (POST request).")
                                .version("1.0")
                )
                .servers(List.of(server))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "Authorization",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
