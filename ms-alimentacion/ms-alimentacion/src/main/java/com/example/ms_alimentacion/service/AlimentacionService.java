package com.example.ms_alimentacion.service;

import com.example.ms_alimentacion.model.Alimentacion;
import com.example.ms_alimentacion.repository.AlimentacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AlimentacionService {
    private static final Logger log = LoggerFactory.getLogger(AlimentacionService.class);

    @Autowired
    private AlimentacionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Alimentacion registrarAlimentacion(Alimentacion alimentacion) {
        log.info("Validando Regla R3 (TCA vs Biomasa) para jaula ID: " + alimentacion.getJaulaId());

        try {
            String url = "http://localhost:8081/api/v1/biomasa/total/" + alimentacion.getJaulaId();
            Double biomasaTotal = restTemplate.getForObject(url, Double.class);

            if (biomasaTotal == null || biomasaTotal == 0) {
                log.error("Denegado: No hay cálculos de biomasa para la jaula " + alimentacion.getJaulaId());
                throw new RuntimeException("No se puede alimentar una jaula sin datos de biomasa.");
            }

            Double limiteMaximo = biomasaTotal * 0.03;

            if (alimentacion.getCantidadAlimentoKilos() > limiteMaximo) {
                log.warn("Violación R3: " + alimentacion.getCantidadAlimentoKilos() + "kg supera el 3% (" + limiteMaximo + "kg)");
                throw new RuntimeException("Exceso de ración. El límite máximo permitido es " + limiteMaximo + " kg.");
            }

            return repository.save(alimentacion);

        } catch (Exception e) {
            log.error("Fallo de comunicación distribuida con ms-biomasa: " + e.getMessage());
            throw e;
        }
    }
}
