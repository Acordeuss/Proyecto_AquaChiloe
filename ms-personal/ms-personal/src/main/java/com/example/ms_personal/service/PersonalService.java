package com.example.ms_personal.service;

import com.example.ms_personal.model.Trabajador;
import com.example.ms_personal.repository.TrabajadorRepository;
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
        log.info("Extrayendo nómina general de trabajadores activos en planta.");
        return repository.findAll();
    }

    public Trabajador registrarTrabajador(Trabajador t) {
        log.info("Registrando alta de colaborador. RUT: " + t.getRut() + " en turno: " + t.getTurno());
        return repository.save(t);
    }
}