package com.example.ms_lotes.service;

import com.example.ms_lotes.model.Lote;
import com.example.ms_lotes.repository.LotesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class LotesService {
    private static final Logger log = LoggerFactory.getLogger(LotesService.class);

    @Autowired
    private LotesRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.ms-centros.url}")
    private String centrosUrl;

    public Lote crearLote(Lote lote) {
        log.info("Validando jaula en ms-centros para jaula ID: " + lote.getJaulaId());

        String url = centrosUrl + "/api/v1/centros/jaulas/" + lote.getJaulaId() + "/verificar";
        Boolean jaulaExiste;

        try {
            jaulaExiste = restTemplate.getForObject(url, Boolean.class);
        } catch (RestClientException e) {
            log.error("Error al validar lote con ms-centros: " + e.getMessage());
            throw new RuntimeException("No se pudo comunicar con ms-centros para validar la jaula.");
        }

        if (jaulaExiste == null || !jaulaExiste) {
            throw new RuntimeException("Error: La jaula ingresada no esta registrada.");
        }

        try {
            return repository.save(lote);
        } catch (DataAccessException e) {
            log.error("Error al guardar lote en la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar el lote en la base de datos.");
        }
    }

    public Integer obtenerPecesPorJaula(Long jaulaId) {
        return repository.findFirstByJaulaIdOrderByIdDesc(jaulaId)
                .map(Lote::getCantidadPeces)
                .orElse(0);
    }
}
