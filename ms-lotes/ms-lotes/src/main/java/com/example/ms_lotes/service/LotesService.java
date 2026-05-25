package com.example.ms_lotes.service;

import com.example.ms_lotes.model.Lote;
import com.example.ms_lotes.repository.LotesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LotesService {
    private static final Logger log = LoggerFactory.getLogger(LotesService.class);

    @Autowired
    private LotesRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Lote crearLote(Lote lote) {
        log.info("Validando infraestructura en ms-centros para jaula ID: " + lote.getJaulaId());

        try {
            String url = "http://localhost:8080/api/v1/centros/jaulas/" + lote.getJaulaId() + "/verificar";
            Boolean jaulaExiste = restTemplate.getForObject(url, Boolean.class);

            if (jaulaExiste == null || !jaulaExiste) {
                log.error("Validación denegada: La jaula " + lote.getJaulaId() + " no existe.");
                throw new RuntimeException("Error: La jaula ingresada no está registrada.");
            }

            log.info("Infraestructura conforme. Guardando nuevo lote.");
            return repository.save(lote);

        } catch (Exception e) {
            log.error("Error de conexión remota con ms-centros: " + e.getMessage());
            throw e;
        }
    }

    public Integer obtenerPecesPorJaula(Long jaulaId) {
        return repository.findFirstByJaulaIdOrderByIdDesc(jaulaId)
                .map(Lote::getCantidadPeces)
                .orElse(0);
    }
}