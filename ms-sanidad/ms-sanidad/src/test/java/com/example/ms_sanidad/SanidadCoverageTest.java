package com.example.ms_sanidad;

import com.example.ms_sanidad.config.OpenApiConfig;
import com.example.ms_sanidad.config.RestClientConfig;
import com.example.ms_sanidad.controller.SanidadController;
import com.example.ms_sanidad.exception.GlobalExceptionHandler;
import com.example.ms_sanidad.model.Sanidad;
import com.example.ms_sanidad.repository.SanidadRepository;
import com.example.ms_sanidad.service.SanidadService;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SanidadCoverageTest {

    @Test
    void serviceRegistraYDetectaBloqueoActivo() {
        SanidadRepository repository = mock(SanidadRepository.class);
        SanidadService service = new SanidadService();
        ReflectionTestUtils.setField(service, "repository", repository);
        Sanidad sanidad = sanidad(LocalDateTime.now().minusDays(1), 5);

        when(repository.save(sanidad)).thenReturn(sanidad);
        when(repository.findByJaulaId(1L)).thenReturn(List.of(sanidad));

        assertSame(sanidad, service.registrarTratamiento(sanidad));
        assertTrue(service.verificarBloqueoSantario(1L));
    }

    @Test
    void serviceCubreSinBloqueoYErrores() {
        SanidadRepository repository = mock(SanidadRepository.class);
        SanidadService service = new SanidadService();
        ReflectionTestUtils.setField(service, "repository", repository);
        Sanidad vencida = sanidad(LocalDateTime.now().minusDays(10), 2);

        when(repository.findByJaulaId(2L)).thenReturn(List.of(vencida));
        when(repository.findByJaulaId(3L)).thenThrow(new DataRetrievalFailureException("db"));
        when(repository.save(vencida)).thenThrow(new DataRetrievalFailureException("db"));

        assertFalse(service.verificarBloqueoSantario(2L));
        assertFalse(service.verificarBloqueoSantario(3L));
        assertThrows(RuntimeException.class, () -> service.registrarTratamiento(vencida));
    }

    @Test
    void controllerConfigYErroresRespondenCorrectamente() {
        SanidadService service = mock(SanidadService.class);
        SanidadController controller = new SanidadController();
        ReflectionTestUtils.setField(controller, "service", service);
        Sanidad sanidad = sanidad(LocalDateTime.now(), 5);

        when(service.registrarTratamiento(sanidad)).thenReturn(sanidad);
        when(service.verificarBloqueoSantario(1L)).thenReturn(true);

        assertEquals(201, controller.crearTratamiento(sanidad).getStatusCode().value());
        assertEquals(Boolean.TRUE, controller.comprobarCarencia(1L).getBody());
        assertNotNull(new RestClientConfig().restTemplate());
        assertEquals("AquaChiloe API - Sanidad", new OpenApiConfig().configurarOpenApi().getInfo().getTitle());

        ResponseEntity<String> error = new GlobalExceptionHandler().manejarError(new RuntimeException("fallo"));
        assertEquals(400, error.getStatusCode().value());
    }

    private Sanidad sanidad(LocalDateTime fecha, int dias) {
        Sanidad sanidad = new Sanidad();
        sanidad.setId(1L);
        sanidad.setJaulaId(1L);
        sanidad.setMedicamento("Tratamiento A");
        sanidad.setFechaAplicacion(fecha);
        sanidad.setDiasCarencia(dias);
        sanidad.setVeterinarioResponsable("Dra. Soto");
        sanidad.setObservaciones("Sin observaciones");
        return sanidad;
    }
}
