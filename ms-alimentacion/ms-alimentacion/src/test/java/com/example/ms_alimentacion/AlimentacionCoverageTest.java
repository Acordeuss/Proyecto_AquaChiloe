package com.example.ms_alimentacion;

import com.example.ms_alimentacion.config.OpenApiConfig;
import com.example.ms_alimentacion.config.RestClientConfig;
import com.example.ms_alimentacion.controller.AlimentacionController;
import com.example.ms_alimentacion.exception.GlobalExceptionHandler;
import com.example.ms_alimentacion.model.Alimentacion;
import com.example.ms_alimentacion.repository.AlimentacionRepository;
import com.example.ms_alimentacion.service.AlimentacionService;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlimentacionCoverageTest {

    @Test
    void serviceRegistraAlimentacionDentroDelLimite() {
        AlimentacionRepository repository = mock(AlimentacionRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AlimentacionService service = service(repository, restTemplate);
        Alimentacion alimentacion = alimentacion(20.0);

        when(restTemplate.getForObject("http://biomasa/api/v1/biomasa/total/1", Double.class)).thenReturn(1000.0);
        when(repository.save(alimentacion)).thenReturn(alimentacion);

        assertSame(alimentacion, service.registrarAlimentacion(alimentacion));
    }

    @Test
    void serviceRechazaSinBiomasaExcesoYErrorRemoto() {
        AlimentacionService service = service(mock(AlimentacionRepository.class), mock(RestTemplate.class));
        RestTemplate restTemplate = (RestTemplate) ReflectionTestUtils.getField(service, "restTemplate");

        when(restTemplate.getForObject(anyString(), eq(Double.class))).thenReturn(0.0);
        assertThrows(RuntimeException.class, () -> service.registrarAlimentacion(alimentacion(1.0)));

        when(restTemplate.getForObject(anyString(), eq(Double.class))).thenReturn(100.0);
        assertThrows(RuntimeException.class, () -> service.registrarAlimentacion(alimentacion(4.0)));

        when(restTemplate.getForObject(anyString(), eq(Double.class))).thenThrow(new RestClientException("down"));
        assertThrows(RuntimeException.class, () -> service.registrarAlimentacion(alimentacion(1.0)));
    }

    @Test
    void serviceManejaErrorDeBaseDeDatos() {
        AlimentacionRepository repository = mock(AlimentacionRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AlimentacionService service = service(repository, restTemplate);
        Alimentacion alimentacion = alimentacion(20.0);

        when(restTemplate.getForObject(anyString(), eq(Double.class))).thenReturn(1000.0);
        when(repository.save(alimentacion)).thenThrow(new DataRetrievalFailureException("db"));

        assertThrows(RuntimeException.class, () -> service.registrarAlimentacion(alimentacion));
    }

    @Test
    void controllerConfigYErroresRespondenCorrectamente() {
        AlimentacionService service = mock(AlimentacionService.class);
        AlimentacionController controller = new AlimentacionController();
        ReflectionTestUtils.setField(controller, "service", service);
        Alimentacion alimentacion = alimentacion(20.0);

        when(service.registrarAlimentacion(alimentacion)).thenReturn(alimentacion);

        assertEquals(201, controller.crear(alimentacion).getStatusCode().value());
        assertNotNull(new RestClientConfig().restTemplate());
        assertEquals("AquaChiloe API - Alimentacion", new OpenApiConfig().configurarOpenApi().getInfo().getTitle());

        ResponseEntity<String> error = new GlobalExceptionHandler().manejarError(new RuntimeException("fallo"));
        assertEquals(400, error.getStatusCode().value());
    }

    private AlimentacionService service(AlimentacionRepository repository, RestTemplate restTemplate) {
        AlimentacionService service = new AlimentacionService();
        ReflectionTestUtils.setField(service, "repository", repository);
        ReflectionTestUtils.setField(service, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(service, "biomasaUrl", "http://biomasa");
        return service;
    }

    private Alimentacion alimentacion(double kilos) {
        Alimentacion alimentacion = new Alimentacion();
        alimentacion.setId(1L);
        alimentacion.setJaulaId(1L);
        alimentacion.setCantidadAlimentoKilos(kilos);
        alimentacion.setFechaRegistro(LocalDate.now());
        return alimentacion;
    }
}
