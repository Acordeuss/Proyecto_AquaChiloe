package com.example.ms_ambiental.config;

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
                        .title("AquaChiloe API - Ambiental")
                        .description("Microservicio para registrar lecturas ambientales.")
                        .version("1.0.0"));
    }
}
