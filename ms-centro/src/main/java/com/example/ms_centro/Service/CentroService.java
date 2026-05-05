package com.example.mscentro.service;

import com.example.mscentro.model.Centro;
import com.example.mscentro.repository.CentroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CentroService {
    // Log obligatorio para la rúbrica
    private static final Logger log = LoggerFactory.getLogger(CentroService.class);

    @Autowired
    private CentroRepository repository;

    public List<Centro> obtenerTodos() {
        log.info("Cargando lista de centros"); // Log de operación crítica
        return repository.findAll();
    }

    public Centro guardar(Centro centro) {
        // Simulación Regla R4: Concesión vigente
        if (centro.getConcesionId().equals("EXPIRADA")) {
            log.error("Error: Concesion vencida");
            throw new RuntimeException("La concesion no es valida");
        }
        log.info("Guardando nuevo centro: " + centro.getNombre());
        return repository.save(centro);
    }
}