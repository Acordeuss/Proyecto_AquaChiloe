package com.example.ms_personal;

import com.example.ms_personal.config.OpenApiConfig;
import com.example.ms_personal.config.RestClientConfig;
import com.example.ms_personal.controller.PersonalController;
import com.example.ms_personal.exception.GlobalExceptionHandler;
import com.example.ms_personal.model.Cargo;
import com.example.ms_personal.model.Trabajador;
import com.example.ms_personal.repository.TrabajadorRepository;
import com.example.ms_personal.service.PersonalService;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonalCoverageTest {

    @Test
    void serviceListaYRegistraTrabajadores() {
        TrabajadorRepository repository = mock(TrabajadorRepository.class);
        PersonalService service = new PersonalService();
        ReflectionTestUtils.setField(service, "repository", repository);
        Trabajador trabajador = trabajador();

        when(repository.findAll()).thenReturn(List.of(trabajador));
        when(repository.save(trabajador)).thenReturn(trabajador);

        assertEquals(1, service.listarTodos().size());
        assertSame(trabajador, service.registrarTrabajador(trabajador));
    }

    @Test
    void serviceManejaErroresDeBaseDeDatos() {
        TrabajadorRepository repository = mock(TrabajadorRepository.class);
        PersonalService service = new PersonalService();
        ReflectionTestUtils.setField(service, "repository", repository);
        Trabajador trabajador = trabajador();

        when(repository.findAll()).thenThrow(new DataRetrievalFailureException("db"));
        when(repository.save(trabajador)).thenThrow(new DataRetrievalFailureException("db"));

        assertThrows(RuntimeException.class, service::listarTodos);
        assertThrows(RuntimeException.class, () -> service.registrarTrabajador(trabajador));
    }

    @Test
    void controllerConfigModelosYErroresRespondenCorrectamente() {
        PersonalService service = mock(PersonalService.class);
        PersonalController controller = new PersonalController();
        ReflectionTestUtils.setField(controller, "service", service);
        Trabajador trabajador = trabajador();

        when(service.listarTodos()).thenReturn(List.of(trabajador));
        when(service.registrarTrabajador(trabajador)).thenReturn(trabajador);

        assertEquals(1, controller.listar().size());
        assertSame(trabajador, controller.crear(trabajador));
        assertNotNull(new RestClientConfig().restTemplate());
        assertEquals("AquaChiloe API - Personal", new OpenApiConfig().configurarOpenApi().getInfo().getTitle());

        ResponseEntity<String> error = new GlobalExceptionHandler().manejarError(new RuntimeException("fallo"));
        assertEquals(400, error.getStatusCode().value());
    }

    private Trabajador trabajador() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setNombreCargo("Operario");

        Trabajador trabajador = new Trabajador();
        trabajador.setId(1L);
        trabajador.setRut("11111111-1");
        trabajador.setNombre("Ana Perez");
        trabajador.setTurno("Manana");
        trabajador.setCargo(cargo);
        return trabajador;
    }
}
