package com.example.ms_personal.service;

import com.example.ms_personal.model.Trabajador;
import com.example.ms_personal.repository.TrabajadorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonalService {
    private static final Logger log = LoggerFactory.getLogger(PersonalService.class);

    @Autowired
    private TrabajadorRepository repository;

    public List<Trabajador> listarTodos() {
        log.info("Listando trabajadores.");

        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            log.error("Error al consultar trabajadores en la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo consultar la base de datos de personal.");
        }
    }

    public Trabajador registrarTrabajador(Trabajador trabajador) {
        log.info("Registrando trabajador. RUT: " + trabajador.getRut());

        try {
            return repository.save(trabajador);
        } catch (DataAccessException e) {
            log.error("Error al guardar trabajador en la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar el trabajador en la base de datos.");
        }
    }
}
