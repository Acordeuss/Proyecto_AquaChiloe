package com.example.ms_alimentacion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("AquaChiloe API - Alimentacion")
                        .description("Microservicio para registrar alimentacion de jaulas.")
                        .version("1.0.0"));
    }
}
