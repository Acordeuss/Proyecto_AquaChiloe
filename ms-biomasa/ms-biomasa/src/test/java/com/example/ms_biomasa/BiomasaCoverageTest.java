package com.example.ms_biomasa;

import com.example.ms_biomasa.config.OpenApiConfig;
import com.example.ms_biomasa.config.RestClientConfig;
import com.example.ms_biomasa.controller.BiomasaController;
import com.example.ms_biomasa.exception.GlobalExceptionHandler;
import com.example.ms_biomasa.model.Biomasa;
import com.example.ms_biomasa.repository.BiomasaRepository;
import com.example.ms_biomasa.service.BiomasaService;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BiomasaCoverageTest {

    @Test
    void serviceRegistraMuestreoConCantidadDeLotesYCalculaTotal() {
        BiomasaRepository repository = mock(BiomasaRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        BiomasaService service = service(repository, restTemplate);
        Biomasa biomasa = biomasa();

        when(restTemplate.getForObject("http://lotes/api/v1/lotes/cantidad-peces/1", Integer.class)).thenReturn(1000);
        when(repository.save(biomasa)).thenReturn(biomasa);
        when(repository.findFirstByJaulaIdOrderByIdDesc(1L)).thenReturn(Optional.of(biomasa));

        assertEquals(1000, service.registrarMuestreo(biomasa).getCantidadPeces());
        assertEquals(500.0, service.calcularBiomasaTotalKilos(1L));
    }

    @Test
    void serviceUsaCeroSiLotesNoRespondeONoHayDatos() {
        BiomasaRepository repository = mock(BiomasaRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        BiomasaService service = service(repository, restTemplate);
        Biomasa biomasa = biomasa();

        when(restTemplate.getForObject(anyString(), eq(Integer.class))).thenThrow(new RestClientException("down"));
        when(repository.save(biomasa)).thenReturn(biomasa);
        when(repository.findFirstByJaulaIdOrderByIdDesc(9L)).thenReturn(Optional.empty());

        assertEquals(0, service.registrarMuestreo(biomasa).getCantidadPeces());
        assertEquals(0.0, service.calcularBiomasaTotalKilos(9L));
    }

    @Test
    void serviceManejaCantidadNulaYErrorDeBaseDeDatos() {
        BiomasaRepository repository = mock(BiomasaRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        BiomasaService service = service(repository, restTemplate);
        Biomasa biomasa = biomasa();

        when(restTemplate.getForObject(anyString(), eq(Integer.class))).thenReturn(null);
        when(repository.save(biomasa)).thenThrow(new DataRetrievalFailureException("db"));

        assertThrows(RuntimeException.class, () -> service.registrarMuestreo(biomasa));
        assertEquals(0, biomasa.getCantidadPeces());
    }

    @Test
    void controllerConfigYErroresRespondenCorrectamente() {
        BiomasaService service = mock(BiomasaService.class);
        BiomasaController controller = new BiomasaController();
        ReflectionTestUtils.setField(controller, "service", service);
        Biomasa biomasa = biomasa();

        when(service.registrarMuestreo(biomasa)).thenReturn(biomasa);
        when(service.calcularBiomasaTotalKilos(1L)).thenReturn(500.0);

        assertEquals(201, controller.crearMuestreo(biomasa).getStatusCode().value());
        assertEquals(500.0, controller.obtenerTotalKilos(1L).getBody());
        assertNotNull(new RestClientConfig().restTemplate());
        assertEquals("AquaChiloe API - Biomasa", new OpenApiConfig().configurarOpenApi().getInfo().getTitle());

        ResponseEntity<String> error = new GlobalExceptionHandler().manejarError(new RuntimeException("fallo"));
        assertEquals(400, error.getStatusCode().value());
    }

    private BiomasaService service(BiomasaRepository repository, RestTemplate restTemplate) {
        BiomasaService service = new BiomasaService();
        ReflectionTestUtils.setField(service, "repository", repository);
        ReflectionTestUtils.setField(service, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(service, "lotesUrl", "http://lotes");
        return service;
    }

    private Biomasa biomasa() {
        Biomasa biomasa = new Biomasa();
        biomasa.setId(1L);
        biomasa.setJaulaId(1L);
        biomasa.setEspecie("Salmon");
        biomasa.setPesoPromedioGramos(500.0);
        biomasa.setCantidadPeces(1000);
        biomasa.setFechaMuestreo(LocalDate.now());
        return biomasa;
    }
}
