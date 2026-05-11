package com.aquachiloe.sanidad.controller;

import com.aquachiloe.sanidad.model.Sanidad;
import com.aquachiloe.sanidad.service.SanidadService;
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
    public ResponseEntity<Sanidad> crear(@Valid @RequestBody Sanidad sanidad) {
        return ResponseEntity.status(201).body(service.registrarTratamiento(sanidad));
    }

    // Endpoint clave para ms-cosecha
    @GetMapping("/verificar-bloqueo/{jaulaId}")
    public ResponseEntity<Boolean> estaBloqueado(@PathVariable Long jaulaId) {
        boolean bloqueado = service.estaEnCarencia(jaulaId);
        return ResponseEntity.ok(bloqueado);
    }
}
