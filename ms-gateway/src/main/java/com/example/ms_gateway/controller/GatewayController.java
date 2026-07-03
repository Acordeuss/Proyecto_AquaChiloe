package com.example.ms_gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private final RestTemplate restTemplate;

    @Value("${services.centros}")
    private String centrosUrl;

    @Value("${services.biomasa}")
    private String biomasaUrl;

    @Value("${services.lotes}")
    private String lotesUrl;

    @Value("${services.alimentacion}")
    private String alimentacionUrl;

    @Value("${services.ambiental}")
    private String ambientalUrl;

    @Value("${services.sanidad}")
    private String sanidadUrl;

    @Value("${services.personal}")
    private String personalUrl;

    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/{servicio}/**")
    @Operation(summary = "Reenviar peticion a microservicio", description = "Centraliza las rutas bajo /gateway/{servicio}/** y reenvia la peticion al microservicio correspondiente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Respuesta entregada por el microservicio destino"),
            @ApiResponse(responseCode = "404", description = "Servicio no registrado en el gateway"),
            @ApiResponse(responseCode = "503", description = "Microservicio destino no disponible")
    })
    public ResponseEntity<String> reenviar(
            @Parameter(description = "Nombre del microservicio: centros, biomasa, lotes, alimentacion, ambiental, sanidad o personal")
            @PathVariable String servicio,
            HttpServletRequest request,
            @RequestBody(required = false) String body) {

        String baseUrl = obtenerUrl(servicio);
        String ruta = request.getRequestURI().replace("/gateway/" + servicio, "");
        String query = request.getQueryString() == null ? "" : "?" + request.getQueryString();
        String urlFinal = baseUrl + ruta + query;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", request.getContentType() == null ? "application/json" : request.getContentType());

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            HttpMethod metodo = HttpMethod.valueOf(request.getMethod());

            return restTemplate.exchange(urlFinal, metodo, entity, String.class);
        } catch (RestClientException e) {
            return ResponseEntity.status(503).body("No se pudo conectar con el microservicio: " + servicio);
        }
    }

    private String obtenerUrl(String servicio) {
        return switch (servicio) {
            case "centros" -> centrosUrl;
            case "biomasa" -> biomasaUrl;
            case "lotes" -> lotesUrl;
            case "alimentacion" -> alimentacionUrl;
            case "ambiental" -> ambientalUrl;
            case "sanidad" -> sanidadUrl;
            case "personal" -> personalUrl;
            default -> throw new IllegalArgumentException("Servicio no existe: " + servicio);
        };
    }
}
