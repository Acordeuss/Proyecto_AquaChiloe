package com.example.ms_personal.controller;

import com.example.ms_personal.model.Trabajador;
import com.example.ms_personal.service.PersonalService;
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