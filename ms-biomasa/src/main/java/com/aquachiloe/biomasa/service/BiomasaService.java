package com.aquachiloe.biomasa.service;

import com.aquachiloe.biomasa.model.Biomasa;
import com.aquachiloe.biomasa.repository.BiomasaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BiomasaService {
    private static final Logger log = LoggerFactory.getLogger(BiomasaService.class);

    @Autowired
    private BiomasaRepository repository;

    public Biomasa registrar(Biomasa biomasa) {
        log.info("Registrando biomasa para jaula con ID: " + biomasa.getIdJaula());
        return repository.save(biomasa);
    }

    public Double calcularBiomasaTotalKilos(Long idJaula) {
        log.info("Calculando biomasa total remota para jaula ID: " + idJaula);
        return repository.findFirstByIdJaulaOrderByIdRegistroBiomasaDesc(idJaula)
                .map(b -> (b.getPesoPromedio() * b.getCantidadPeces()) / 1000.0)
                .orElse(0.0);
    }
}
