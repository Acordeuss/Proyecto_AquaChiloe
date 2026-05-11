package com.aquachiloe.alimentacion.service;

import com.aquachiloe.alimentacion.model.Alimentacion;
import com.aquachiloe.alimentacion.repository.AlimentacionRepository;
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

    private final RestTemplate restTemplate = new RestTemplate();

    public Alimentacion registrarAlimentacion(Alimentacion alimentacion) {
        log.info("Iniciando validacion R3 para jaula: " + alimentacion.getJaulaId());

        try {
            // Comunicación con ms-biomasa para obtener la biomasa total actual
            String url = "http://localhost:8081/api/v1/biomasa/total/" + alimentacion.getJaulaId();
            Double biomasaTotal = restTemplate.getForObject(url, Double.class);

            if (biomasaTotal == null || biomasaTotal == 0) {
                log.error("No se puede alimentar: No hay registros de biomasa para la jaula");
                throw new RuntimeException("Error: Jaula sin registro de biomasa previo");
            }

            // REGLA R3: El alimento no puede superar el 3% de la biomasa
            Double limiteMaximo = biomasaTotal * 0.03;

            if (alimentacion.getCantidadAlimentoKilos() > limiteMaximo) {
                log.error("VIOLACION REGLA R3: Cantidad " + alimentacion.getCantidadAlimentoKilos() + 
                          " kg supera el limite de " + limiteMaximo + " kg");
                throw new RuntimeException("Sobrepaso del 3% de biomasa permitido");
            }

            log.info("Validacion exitosa. Guardando registro de alimentacion.");
            return repository.save(alimentacion);

        } catch (Exception e) {
            log.error("Error en proceso de alimentacion: " + e.getMessage());
            throw e;
        }
    }
}
