package com.example.ms_lotes;

import com.example.ms_lotes.config.OpenApiConfig;
import com.example.ms_lotes.config.RestClientConfig;
import com.example.ms_lotes.controller.LotesController;
import com.example.ms_lotes.exception.GlobalExceptionHandler;
import com.example.ms_lotes.model.Lote;
import com.example.ms_lotes.repository.LotesRepository;
import com.example.ms_lotes.service.LotesService;
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

class LotesCoverageTest {

    @Test
    void serviceCreaLoteSiJaulaExisteYConsultaPeces() {
        LotesRepository repository = mock(LotesRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        LotesService service = service(repository, restTemplate);
        Lote lote = lote();

        when(restTemplate.getForObject("http://centros/api/v1/centros/jaulas/1/verificar", Boolean.class)).thenReturn(true);
        when(repository.save(lote)).thenReturn(lote);
        when(repository.findFirstByJaulaIdOrderByIdDesc(1L)).thenReturn(Optional.of(lote));

        assertSame(lote, service.crearLote(lote));
        assertEquals(2000, service.obtenerPecesPorJaula(1L));
    }

    @Test
    void serviceRechazaJaulaInexistenteYErroresRemotos() {
        LotesService service = service(mock(LotesRepository.class), mock(RestTemplate.class));
        RestTemplate restTemplate = (RestTemplate) ReflectionTestUtils.getField(service, "restTemplate");
        Lote lote = lote();

        when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(false);
        assertThrows(RuntimeException.class, () -> service.crearLote(lote));

        when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenThrow(new RestClientException("down"));
        assertThrows(RuntimeException.class, () -> service.crearLote(lote));
    }

    @Test
    void serviceManejaErrorDeBaseDeDatosYSinLotes() {
        LotesRepository repository = mock(LotesRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        LotesService service = service(repository, restTemplate);
        Lote lote = lote();

        when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(true);
        when(repository.save(lote)).thenThrow(new DataRetrievalFailureException("db"));
        when(repository.findFirstByJaulaIdOrderByIdDesc(9L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.crearLote(lote));
        assertEquals(0, service.obtenerPecesPorJaula(9L));
    }

    @Test
    void controllerConfigYErroresRespondenCorrectamente() {
        LotesService service = mock(LotesService.class);
        LotesController controller = new LotesController();
        ReflectionTestUtils.setField(controller, "service", service);
        Lote lote = lote();

        when(service.crearLote(lote)).thenReturn(lote);
        when(service.obtenerPecesPorJaula(1L)).thenReturn(2000);

        assertEquals(201, controller.registrarLote(lote).getStatusCode().value());
        assertEquals(2000, controller.obtenerCantidadPeces(1L).getBody());
        assertNotNull(new RestClientConfig().restTemplate());
        assertEquals("AquaChiloe API - Lotes", new OpenApiConfig().configurarOpenApi().getInfo().getTitle());

        ResponseEntity<String> error = new GlobalExceptionHandler().manejarError(new RuntimeException("fallo"));
        assertEquals(400, error.getStatusCode().value());
    }

    @Test
    void entidadLegadaDeLotesQuedaCubierta() {
        com.aquachiloe.lotes.entity.Lote lote = new com.aquachiloe.lotes.entity.Lote();
        LocalDate fecha = LocalDate.now();

        lote.setId(10L);
        lote.setEspecie("Salmon Atlantico");
        lote.setOrigenOvas("Piscicultura sur");
        lote.setCantidadPeces(3000);
        lote.setPesoPromedioInicial(0.25);
        lote.setJaulaId(4L);
        lote.setFechaIngreso(fecha);
        lote.setActivo(false);

        assertEquals(10L, lote.getId());
        assertEquals("Salmon Atlantico", lote.getEspecie());
        assertEquals("Piscicultura sur", lote.getOrigenOvas());
        assertEquals(3000, lote.getCantidadPeces());
        assertEquals(0.25, lote.getPesoPromedioInicial());
        assertEquals(4L, lote.getJaulaId());
        assertEquals(fecha, lote.getFechaIngreso());
        assertFalse(lote.isActivo());
    }

    private LotesService service(LotesRepository repository, RestTemplate restTemplate) {
        LotesService service = new LotesService();
        ReflectionTestUtils.setField(service, "repository", repository);
        ReflectionTestUtils.setField(service, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(service, "centrosUrl", "http://centros");
        return service;
    }

    private Lote lote() {
        Lote lote = new Lote();
        lote.setId(1L);
        lote.setCodigoLote("L-01");
        lote.setJaulaId(1L);
        lote.setCantidadPeces(2000);
        lote.setFechaIngreso(LocalDate.now());
        return lote;
    }
}
