package com.example.ms_biomasa.service;

import com.example.ms_biomasa.model.Biomasa;
import com.example.ms_biomasa.repository.BiomasaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BiomasaService {
    private static final Logger log = LoggerFactory.getLogger(BiomasaService.class);

    @Autowired
    private BiomasaRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Biomasa registrarMuestreo(Biomasa biomasa) {
        log.info("Sincronizando población con ms-lotes para jaula: " + biomasa.getJaulaId());

        try {
            String url = "http://localhost:8082/api/v1/lotes/cantidad-peces/" + biomasa.getJaulaId();
            Integer cantidadOficial = restTemplate.getForObject(url, Integer.class);

            if (cantidadOficial == null || cantidadOficial == 0) {
                log.warn("Sin respuesta de stock oficial. Registrando población en 0.");
                biomasa.setCantidadPeces(0);
            } else {
                biomasa.setCantidadPeces(cantidadOficial);
            }

        } catch (Exception e) {
            log.error("Fallo de comunicación distribuida. Fallback activo: " + e.getMessage());
            biomasa.setCantidadPeces(0);
        }

        return repository.save(biomasa);
    }

    public Double calcularBiomasaTotalKilos(Long jaulaId) {
        return repository.findFirstByJaulaIdOrderByIdDesc(jaulaId)
                .map(b -> (b.getPesoPromedioGramos() * b.getCantidadPeces()) / 1000.0)
                .orElse(0.0);
    }
}