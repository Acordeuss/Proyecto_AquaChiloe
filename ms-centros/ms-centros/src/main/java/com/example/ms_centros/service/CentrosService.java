package com.example.ms_centros.service;

import com.example.ms_centros.model.Jaula;
import com.example.ms_centros.repository.JaulaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CentrosService {
    private static final Logger log = LoggerFactory.getLogger(CentrosService.class);

    @Autowired
    private JaulaRepository jaulaRepository;

    public Jaula registrarJaula(Jaula jaula) {
        log.info("Registrando nueva jaula " + jaula.getCodigoJaula() + " para el centro ID: " + jaula.getCentroId());

        try {
            return jaulaRepository.save(jaula);
        } catch (DataAccessException e) {
            log.error("Error al guardar jaula en la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar la jaula en la base de datos.");
        }
    }

    public boolean verificarExistenciaJaula(Long jaulaId) {
        log.info("Verificando estado de jaula ID: " + jaulaId);

        try {
            return jaulaRepository.findById(jaulaId)
                    .map(Jaula::getActiva)
                    .orElse(false);
        } catch (DataAccessException e) {
            log.error("Error al consultar jaula en la base de datos: " + e.getMessage());
            return false;
        }
    }
}
