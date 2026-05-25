package com.example.ms_centros.service;

import com.example.ms_centros.model.Jaula;
import com.example.ms_centros.repository.JaulaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CentrosService {
    private static final Logger log = LoggerFactory.getLogger(CentrosService.class);

    @Autowired
    private JaulaRepository jaulaRepository;

    public Jaula registrarJaula(Jaula jaula) {
        log.info("Registrando nueva jaula " + jaula.getCodigoJaula() + " para el centro ID: " + jaula.getCentroId());
        return jaulaRepository.save(jaula);
    }

    public boolean verificarExistenciaJaula(Long jaulaId) {
        log.info("Verificando estado operativo de infraestructura para jaula ID: " + jaulaId);
        return jaulaRepository.findById(jaulaId)
                .map(Jaula::getActiva)
                .orElse(false);
    }
}
