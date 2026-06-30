package com.example.ms_ambiental.service;

import com.example.ms_ambiental.model.LecturaAmbiental;
import com.example.ms_ambiental.repository.AmbientalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AmbientalService {
    private static final Logger log = LoggerFactory.getLogger(AmbientalService.class);

    @Autowired
    private AmbientalRepository repository;

    public LecturaAmbiental registrarLectura(LecturaAmbiental lectura) {
        log.info("Evaluando lectura ambiental del sensor: " + lectura.getSensorId());

        if (lectura.getOxigeno() < 6.0) {
            log.warn("Alerta critica por oxigeno bajo: " + lectura.getOxigeno());
            lectura.setAlertaCritica(true);
        }

        try {
            return repository.save(lectura);
        } catch (DataAccessException e) {
            log.error("Error al guardar lectura ambiental en la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar la lectura ambiental en la base de datos.");
        }
    }

    public boolean hayAlertaActiva(Long centroId) {
        try {
            return repository.findByCentroIdOrderByFechaLecturaDesc(centroId)
                    .stream()
                    .findFirst()
                    .map(LecturaAmbiental::getAlertaCritica)
                    .orElse(false);
        } catch (DataAccessException e) {
            log.error("Error al consultar alertas en la base de datos: " + e.getMessage());
            return false;
        }
    }
}
