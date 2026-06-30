package com.example.ms_sanidad.service;

import com.example.ms_sanidad.model.Sanidad;
import com.example.ms_sanidad.repository.SanidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SanidadService {
    private static final Logger log = LoggerFactory.getLogger(SanidadService.class);

    @Autowired
    private SanidadRepository repository;

    public Sanidad registrarTratamiento(Sanidad sanidad) {
        log.info("Registrando tratamiento para medicamento: " + sanidad.getMedicamento());

        try {
            return repository.save(sanidad);
        } catch (DataAccessException e) {
            log.error("Error al guardar tratamiento en la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar el tratamiento en la base de datos.");
        }
    }

    public boolean verificarBloqueoSantario(Long jaulaId) {
        log.info("Verificando periodo de carencia para jaula ID: " + jaulaId);

        try {
            List<Sanidad> tratamientos = repository.findByJaulaId(jaulaId);
            LocalDateTime ahora = LocalDateTime.now();

            return tratamientos.stream().anyMatch(t -> {
                LocalDateTime fechaFinCarencia = t.getFechaAplicacion().plusDays(t.getDiasCarencia());
                return ahora.isBefore(fechaFinCarencia);
            });
        } catch (DataAccessException e) {
            log.error("Error al consultar sanidad en la base de datos: " + e.getMessage());
            return false;
        }
    }
}
