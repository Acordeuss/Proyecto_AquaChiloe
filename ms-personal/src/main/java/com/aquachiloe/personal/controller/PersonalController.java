package com.aquachiloe.personal.controller;

import com.aquachiloe.personal.model.Trabajador;
import com.aquachiloe.personal.service.PersonalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/personal")
public class PersonalController {

    @Autowired
    private PersonalService service;

    @GetMapping("/")
    public List<Trabajador> listar() {
        return service.listarTodos();
    }

    @PostMapping("/")
    public Trabajador crear(@Valid @RequestBody Trabajador t) {
        return service.registrarTrabajador(t);
    }
}
