package com.example.ms_centros;

import com.example.ms_centros.config.OpenApiConfig;
import com.example.ms_centros.config.RestClientConfig;
import com.example.ms_centros.controller.CentrosController;
import com.example.ms_centros.exception.GlobalExceptionHandler;
import com.example.ms_centros.model.Jaula;
import com.example.ms_centros.repository.JaulaRepository;
import com.example.ms_centros.service.CentrosService;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CentrosCoverageTest {

    @Test
    void serviceRegistraYVerificaJaulas() {
        JaulaRepository repository = mock(JaulaRepository.class);
        CentrosService service = new CentrosService();
        ReflectionTestUtils.setField(service, "jaulaRepository", repository);
        Jaula jaula = jaula();

        when(repository.save(jaula)).thenReturn(jaula);
        when(repository.findById(1L)).thenReturn(Optional.of(jaula));
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertSame(jaula, service.registrarJaula(jaula));
        assertTrue(service.verificarExistenciaJaula(1L));
        assertFalse(service.verificarExistenciaJaula(2L));
    }

    @Test
    void serviceManejaErroresDeBaseDeDatos() {
        JaulaRepository repository = mock(JaulaRepository.class);
        CentrosService service = new CentrosService();
        ReflectionTestUtils.setField(service, "jaulaRepository", repository);
        Jaula jaula = jaula();

        when(repository.save(jaula)).thenThrow(new DataRetrievalFailureException("db"));
        when(repository.findById(1L)).thenThrow(new DataRetrievalFailureException("db"));

        assertThrows(RuntimeException.class, () -> service.registrarJaula(jaula));
        assertFalse(service.verificarExistenciaJaula(1L));
    }

    @Test
    void controllerConfigYErroresRespondenCorrectamente() {
        CentrosService service = mock(CentrosService.class);
        CentrosController controller = new CentrosController();
        ReflectionTestUtils.setField(controller, "service", service);
        Jaula jaula = jaula();

        when(service.registrarJaula(jaula)).thenReturn(jaula);
        when(service.verificarExistenciaJaula(1L)).thenReturn(true);

        assertEquals(201, controller.crearJaula(jaula).getStatusCode().value());
        assertEquals(Boolean.TRUE, controller.verificarJaula(1L).getBody());
        assertNotNull(new RestClientConfig().restTemplate());
        assertEquals("AquaChiloe API - Centros", new OpenApiConfig().configurarOpenApi().getInfo().getTitle());

        ResponseEntity<String> error = new GlobalExceptionHandler().manejarError(new RuntimeException("fallo"));
        assertEquals(400, error.getStatusCode().value());
        assertEquals("fallo", error.getBody());
    }

    private Jaula jaula() {
        Jaula jaula = new Jaula();
        jaula.setId(1L);
        jaula.setCodigoJaula("J-01");
        jaula.setCentroId(10L);
        jaula.setActiva(true);
        return jaula;
    }
}
