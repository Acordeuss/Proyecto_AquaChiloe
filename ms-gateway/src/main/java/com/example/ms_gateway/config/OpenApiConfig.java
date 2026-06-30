package com.example.ms_gateway.config;

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
                        .title("AquaChiloe API - Gateway")
                        .description("Gateway simple para centralizar las rutas de los microservicios.")
                        .version("1.0.0"));
    }
}
