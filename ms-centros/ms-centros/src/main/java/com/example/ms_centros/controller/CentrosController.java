package com.example.ms_centros.controller;

import com.example.ms_centros.model.Jaula;
import com.example.ms_centros.service.CentrosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/centros")
public class CentrosController {

    @Autowired
    private CentrosService service;

    @PostMapping("/jaulas")
    public ResponseEntity<Jaula> crearJaula(@Valid @RequestBody Jaula jaula) {
        return ResponseEntity.status(201).body(service.registrarJaula(jaula));
    }

    @GetMapping("/jaulas/{id}/verificar")
    public ResponseEntity<Boolean> verificarJaula(@PathVariable Long id) {
        return ResponseEntity.ok(service.verificarExistenciaJaula(id));
    }
}