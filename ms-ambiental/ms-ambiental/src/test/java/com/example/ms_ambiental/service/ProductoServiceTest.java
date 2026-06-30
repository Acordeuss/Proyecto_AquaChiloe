package com.example.ms_ambiental.service;

import com.example.ms_ambiental.model.LecturaAmbiental;
import com.example.ms_ambiental.repository.AmbientalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ProductoServiceTest {

    @Autowired
    private AmbientalService service;

    @Autowired
    private AmbientalRepository repository;

    @Test
    void registrarLecturaGuardaEnBaseDeDatosYActivaAlerta() {
        LecturaAmbiental lectura = new LecturaAmbiental();
        lectura.setCentroId(1L);
        lectura.setSensorId("SENSOR-01");
        lectura.setOxigeno(5.2);
        lectura.setTemperatura(12.5);
        lectura.setSalinidad(30.0);

        LecturaAmbiental guardada = service.registrarLectura(lectura);

        assertNotNull(guardada.getId());
        assertTrue(guardada.getAlertaCritica());
        assertTrue(repository.findById(guardada.getId()).isPresent());
    }
}
