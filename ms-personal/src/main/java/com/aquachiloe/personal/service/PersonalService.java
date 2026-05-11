package com.aquachiloe.personal.service;

import com.aquachiloe.personal.model.Trabajador;
import com.aquachiloe.personal.repository.TrabajadorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonalService {
    private static final Logger log = LoggerFactory.getLogger(PersonalService.class);

    @Autowired
    private TrabajadorRepository repository;

    public List<Trabajador> listarTodos() {
        log.info("Consultando nomina completa de trabajadores");
        return repository.findAll();
    }

    public Trabajador registrarTrabajador(Trabajador t) {
        log.info("Registrando nuevo trabajador: " + t.getNombre() + " con RUT: " + t.getRut());
        return repository.save(t);
    }
}
