package com.example.ms_ambiental.controller;

import com.example.ms_ambiental.model.LecturaAmbiental;
import com.example.ms_ambiental.service.AmbientalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ambiental")
public class AmbientalController {

    @Autowired
    private AmbientalService service;

    @PostMapping("/lectura")
    public ResponseEntity<LecturaAmbiental> recibirLectura(@Valid @RequestBody LecturaAmbiental lectura) {
        return ResponseEntity.status(201).body(service.registrarLectura(lectura));
    }

    @GetMapping("/status-critico/{centroId}")
    public ResponseEntity<Boolean> verificarAlerta(@PathVariable Long centroId) {
        return ResponseEntity.ok(service.hayAlertaActiva(centroId));
    }
}