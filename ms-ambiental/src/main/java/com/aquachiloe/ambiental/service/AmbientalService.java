package com.aquachiloe.ambiental.service;

import com.aquachiloe.ambiental.model.LecturaAmbiental;
import com.aquachiloe.ambiental.repository.AmbientalRepository;
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
        log.info("Procesando lectura de sensor: " + lectura.getSensorId() + " en Centro: " + lectura.getCentroId());

        // REGLA R5: Si Oxígeno < 6 mg/L, activar alerta crítica
        if (lectura.getOxigeno() < 6.0) {
            log.warn("¡ALERTA CRÍTICA! Oxigeno bajo en Centro " + lectura.getCentroId() + ": " + lectura.getOxigeno() + " mg/L");
            lectura.setAlertaCritica(true);
        }

        return repository.save(lectura);
    }

    public boolean hayAlertaActiva(Long centroId) {
        // Verifica si la última lectura del centro tiene una alerta activa
        return repository.findByCentroIdOrderByFechaLecturaDesc(centroId)
                .stream()
                .findFirst()
                .map(LecturaAmbiental::getAlertaCritica)
                .orElse(false);
    }
}
