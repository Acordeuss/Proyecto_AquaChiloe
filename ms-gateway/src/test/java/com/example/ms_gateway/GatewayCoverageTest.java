package com.example.ms_gateway;

import com.example.ms_gateway.config.OpenApiConfig;
import com.example.ms_gateway.config.RestClientConfig;
import com.example.ms_gateway.controller.GatewayController;
import com.example.ms_gateway.exception.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GatewayCoverageTest {

    @Test
    void reenviaPeticionAlMicroservicioConfigurado() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        GatewayController controller = controller(restTemplate);
        HttpServletRequest request = request("GET", "/gateway/centros/api/v1/centros/jaulas/1/verificar", null);

        when(restTemplate.exchange(
                eq("http://centros/api/v1/centros/jaulas/1/verificar"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)))
                .thenReturn(ResponseEntity.ok("true"));

        ResponseEntity<String> response = controller.reenviar("centros", request, null);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("true", response.getBody());
    }

    @Test
    void reenviaQueryYRetorna503SiDestinoNoResponde() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        GatewayController controller = controller(restTemplate);
        HttpServletRequest request = request("POST", "/gateway/personal/api/v1/personal/", "activo=true");

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RestClientException("down"));

        ResponseEntity<String> response = controller.reenviar("personal", request, "{}");

        assertEquals(503, response.getStatusCode().value());
        assertTrue(response.getBody().contains("personal"));
        verify(restTemplate).exchange(eq("http://personal/api/v1/personal/?activo=true"), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void servicioDesconocidoYConfiguracionesQuedanCubiertos() {
        GatewayController controller = controller(mock(RestTemplate.class));
        HttpServletRequest request = request("GET", "/gateway/desconocido/api", null);

        assertThrows(IllegalArgumentException.class, () -> controller.reenviar("desconocido", request, null));
        assertNotNull(new RestClientConfig().restTemplate());
        assertEquals("AquaChiloe API - Gateway", new OpenApiConfig().configurarOpenApi().getInfo().getTitle());

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        assertEquals(404, handler.manejarNoEncontrado(new IllegalArgumentException("no")).getStatusCode().value());
        assertEquals(400, handler.manejarError(new RuntimeException("fallo")).getStatusCode().value());
    }

    private GatewayController controller(RestTemplate restTemplate) {
        GatewayController controller = new GatewayController(restTemplate);
        ReflectionTestUtils.setField(controller, "centrosUrl", "http://centros");
        ReflectionTestUtils.setField(controller, "biomasaUrl", "http://biomasa");
        ReflectionTestUtils.setField(controller, "lotesUrl", "http://lotes");
        ReflectionTestUtils.setField(controller, "alimentacionUrl", "http://alimentacion");
        ReflectionTestUtils.setField(controller, "ambientalUrl", "http://ambiental");
        ReflectionTestUtils.setField(controller, "sanidadUrl", "http://sanidad");
        ReflectionTestUtils.setField(controller, "personalUrl", "http://personal");
        return controller;
    }

    private HttpServletRequest request(String method, String uri, String query) {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn(method);
        when(request.getRequestURI()).thenReturn(uri);
        when(request.getQueryString()).thenReturn(query);
        when(request.getContentType()).thenReturn("application/json");
        return request;
    }
}
