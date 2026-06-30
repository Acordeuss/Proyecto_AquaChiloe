package com.example.ms_biomasa.service;

import com.example.ms_biomasa.model.Biomasa;
import com.example.ms_biomasa.repository.BiomasaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class BiomasaService {
    private static final Logger log = LoggerFactory.getLogger(BiomasaService.class);

    @Autowired
    private BiomasaRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.ms-lotes.url}")
    private String lotesUrl;

    public Biomasa registrarMuestreo(Biomasa biomasa) {
        log.info("Consultando cantidad de peces en ms-lotes para jaula: " + biomasa.getJaulaId());

        try {
            String url = lotesUrl + "/api/v1/lotes/cantidad-peces/" + biomasa.getJaulaId();
            Integer cantidadOficial = restTemplate.getForObject(url, Integer.class);

            if (cantidadOficial == null || cantidadOficial == 0) {
                biomasa.setCantidadPeces(0);
            } else {
                biomasa.setCantidadPeces(cantidadOficial);
            }
        } catch (RestClientException e) {
            log.error("No se pudo comunicar con ms-lotes. Se guarda cantidad en 0: " + e.getMessage());
            biomasa.setCantidadPeces(0);
        }

        try {
            return repository.save(biomasa);
        } catch (DataAccessException e) {
            log.error("Error al guardar biomasa en la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar la biomasa en la base de datos.");
        }
    }

    public Double calcularBiomasaTotalKilos(Long jaulaId) {
        return repository.findFirstByJaulaIdOrderByIdDesc(jaulaId)
                .map(b -> (b.getPesoPromedioGramos() * b.getCantidadPeces()) / 1000.0)
                .orElse(0.0);
    }
}
