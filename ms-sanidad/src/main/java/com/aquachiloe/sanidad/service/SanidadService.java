package com.aquachiloe.sanidad.service;

import com.aquachiloe.sanidad.model.Sanidad;
import com.aquachiloe.sanidad.repository.SanidadRepository;
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
        log.info("Registrando tratamiento sanitario: " + sanidad.getMedicamento() + " en jaula " + sanidad.getJaulaId());
        return repository.save(sanidad);
    }

    public boolean estaEnCarencia(Long jaulaId) {
        log.info("Verificando periodo de carencia (Regla R2) para jaula: " + jaulaId);
        List<Sanidad> tratamientos = repository.findByJaulaId(jaulaId);
        
        LocalDateTime ahora = LocalDateTime.now();

        // Si algun tratamiento aun tiene dias de carencia pendientes, la jaula esta bloqueada
        return tratamientos.stream().anyMatch(t -> {
            LocalDateTime fechaFinCarencia = t.getFechaAplicacion().plusDays(t.getDiasCarencia());
            return ahora.isBefore(fechaFinCarencia);
        });
    }
}
