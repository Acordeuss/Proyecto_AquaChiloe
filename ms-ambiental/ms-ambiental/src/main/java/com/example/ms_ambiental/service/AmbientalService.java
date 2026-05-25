package com.example.ms_ambiental.service;

import com.example.ms_ambiental.model.LecturaAmbiental;
import com.example.ms_ambiental.repository.AmbientalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmbientalService {
    private static final Logger log = LoggerFactory.getLogger(AmbientalService.class);

    @Autowired
    private AmbientalRepository repository;

    public LecturaAmbiental registrarLectura(LecturaAmbiental lectura) {
        log.info("Evaluando telemetría ambiental del sensor: " + lectura.getSensorId());

        // Regla R5: Oxígeno por debajo del umbral crítico (6.0 mg/L)
        if (lectura.getOxigeno() < 6.0) {
            log.warn("¡ALERTA CRÍTICA (R5)! Oxígeno disuelto en niveles peligrosos: " + lectura.getOxigeno() + " mg/L en Centro: " + lectura.getCentroId());
            lectura.setAlertaCritica(true);
        }

        return repository.save(lectura);
    }

    public boolean hayAlertaActiva(Long centroId) {
        return repository.findByCentroIdOrderByFechaLecturaDesc(centroId)
                .stream()
                .findFirst()
                .map(LecturaAmbiental::getAlertaCritica)
                .orElse(false);
    }
}