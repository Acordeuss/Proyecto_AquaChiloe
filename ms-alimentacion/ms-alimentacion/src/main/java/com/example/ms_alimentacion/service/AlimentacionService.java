package com.example.ms_alimentacion.service;

import com.example.ms_alimentacion.model.Alimentacion;
import com.example.ms_alimentacion.repository.AlimentacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AlimentacionService {
    private static final Logger log = LoggerFactory.getLogger(AlimentacionService.class);

    @Autowired
    private AlimentacionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.ms-biomasa.url}")
    private String biomasaUrl;

    public Alimentacion registrarAlimentacion(Alimentacion alimentacion) {
        log.info("Validando alimentacion para jaula ID: " + alimentacion.getJaulaId());

        String url = biomasaUrl + "/api/v1/biomasa/total/" + alimentacion.getJaulaId();
        Double biomasaTotal;

        try {
            biomasaTotal = restTemplate.getForObject(url, Double.class);
        } catch (RestClientException e) {
            log.error("Fallo al validar alimentacion con ms-biomasa: " + e.getMessage());
            throw new RuntimeException("No se pudo comunicar con ms-biomasa para validar la biomasa.");
        }

        if (biomasaTotal == null || biomasaTotal == 0) {
            throw new RuntimeException("No se puede alimentar una jaula sin datos de biomasa.");
        }

        Double limiteMaximo = biomasaTotal * 0.03;

        if (alimentacion.getCantidadAlimentoKilos() > limiteMaximo) {
            throw new RuntimeException("Exceso de racion. El limite maximo permitido es " + limiteMaximo + " kg.");
        }

        try {
            return repository.save(alimentacion);
        } catch (DataAccessException e) {
            log.error("Error al guardar alimentacion en la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar la alimentacion en la base de datos.");
        }
    }
}
