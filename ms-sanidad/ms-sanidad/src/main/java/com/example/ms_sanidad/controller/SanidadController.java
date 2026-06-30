package com.example.ms_sanidad.controller;

import com.example.ms_sanidad.model.Sanidad;
import com.example.ms_sanidad.service.SanidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sanidad")
public class SanidadController {

    @Autowired
    private SanidadService service;

    @PostMapping("/")
    public ResponseEntity<Sanidad> crearTratamiento(@Valid @RequestBody Sanidad sanidad) {
        return ResponseEntity.status(201).body(service.registrarTratamiento(sanidad));
    }

    @GetMapping("/verificar-bloqueo/{jaulaId}")
    public ResponseEntity<Boolean> comprobarCarencia(@PathVariable Long jaulaId) {
        return ResponseEntity.ok(service.verificarBloqueoSantario(jaulaId));
    }
}