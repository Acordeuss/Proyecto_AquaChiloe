package com.example.ms_ambiental;

import com.example.ms_ambiental.config.OpenApiConfig;
import com.example.ms_ambiental.config.RestClientConfig;
import com.example.ms_ambiental.controller.AmbientalController;
import com.example.ms_ambiental.exception.GlobalExceptionHandler;
import com.example.ms_ambiental.model.LecturaAmbiental;
import com.example.ms_ambiental.repository.AmbientalRepository;
import com.example.ms_ambiental.service.AmbientalService;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AmbientalCoverageTest {

    @Test
    void serviceRegistraLecturaYDetectaAlerta() {
        AmbientalRepository repository = mock(AmbientalRepository.class);
        AmbientalService service = new AmbientalService();
        ReflectionTestUtils.setField(service, "repository", repository);
        LecturaAmbiental lectura = lectura(5.5);

        when(repository.save(lectura)).thenReturn(lectura);
        when(repository.findByCentroIdOrderByFechaLecturaDesc(1L)).thenReturn(List.of(lectura));

        LecturaAmbiental guardada = service.registrarLectura(lectura);
        assertTrue(guardada.getAlertaCritica());
        assertTrue(service.hayAlertaActiva(1L));
    }

    @Test
    void serviceCubreRamasSinAlertaYErrores() {
        AmbientalRepository repository = mock(AmbientalRepository.class);
        AmbientalService service = new AmbientalService();
        ReflectionTestUtils.setField(service, "repository", repository);
        LecturaAmbiental lectura = lectura(7.1);

        when(repository.save(lectura)).thenReturn(lectura);
        when(repository.findByCentroIdOrderByFechaLecturaDesc(2L)).thenReturn(List.of());
        when(repository.findByCentroIdOrderByFechaLecturaDesc(3L)).thenThrow(new DataRetrievalFailureException("db"));

        assertFalse(service.registrarLectura(lectura).getAlertaCritica());
        assertFalse(service.hayAlertaActiva(2L));
        assertFalse(service.hayAlertaActiva(3L));

        when(repository.save(lectura)).thenThrow(new DataRetrievalFailureException("db"));
        assertThrows(RuntimeException.class, () -> service.registrarLectura(lectura));
    }

    @Test
    void controllerConfigYErroresRespondenCorrectamente() {
        AmbientalService service = mock(AmbientalService.class);
        AmbientalController controller = new AmbientalController();
        ReflectionTestUtils.setField(controller, "service", service);
        LecturaAmbiental lectura = lectura(5.5);

        when(service.registrarLectura(lectura)).thenReturn(lectura);
        when(service.hayAlertaActiva(1L)).thenReturn(true);

        assertEquals(201, controller.recibirLectura(lectura).getStatusCode().value());
        assertEquals(Boolean.TRUE, controller.verificarAlerta(1L).getBody());
        assertNotNull(new RestClientConfig().restTemplate());
        assertEquals("AquaChiloe API - Ambiental", new OpenApiConfig().configurarOpenApi().getInfo().getTitle());

        ResponseEntity<String> error = new GlobalExceptionHandler().manejarError(new RuntimeException("fallo"));
        assertEquals(400, error.getStatusCode().value());
    }

    private LecturaAmbiental lectura(double oxigeno) {
        LecturaAmbiental lectura = new LecturaAmbiental();
        lectura.setId(1L);
        lectura.setCentroId(1L);
        lectura.setSensorId("S-01");
        lectura.setOxigeno(oxigeno);
        lectura.setTemperatura(11.5);
        lectura.setSalinidad(30.0);
        lectura.setFechaLectura(LocalDateTime.now());
        lectura.setAlertaCritica(false);
        return lectura;
    }
}
