package com.example.ms_sanidad.service;

import com.example.ms_sanidad.model.Sanidad;
import com.example.ms_sanidad.repository.SanidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SanidadService {
    private static final Logger log = LoggerFactory.getLogger(SanidadService.class);

    @Autowired
    private SanidadRepository repository;

    public Sanidad registrarTratamiento(Sanidad sanidad) {
        log.info("Registrando tratamiento para el medicamento: " + sanidad.getMedicamento());
        return repository.save(sanidad);
    }

    public boolean verificarBloqueoSantario(Long jaulaId) {
        log.info("Evaluando Regla R2 (Periodo de Carencia) para jaula ID: " + jaulaId);

        List<Sanidad> tratamientos = repository.findByJaulaId(jaulaId);
        LocalDateTime ahora = LocalDateTime.now();

        return tratamientos.stream().anyMatch(t -> {
            LocalDateTime fechaFinCarencia = t.getFechaAplicacion().plusDays(t.getDiasCarencia());
            return ahora.isBefore(fechaFinCarencia);
        });
    }
}
