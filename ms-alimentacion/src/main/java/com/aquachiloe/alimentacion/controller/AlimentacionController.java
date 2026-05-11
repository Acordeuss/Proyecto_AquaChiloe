package com.aquachiloe.alimentacion.controller;

import com.aquachiloe.alimentacion.model.Alimentacion;
import com.aquachiloe.alimentacion.service.AlimentacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alimentacion")
public class AlimentacionController {

    @Autowired
    private AlimentacionService service;

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Alimentacion alimentacion) {
        try {
            return ResponseEntity.status(201).body(service.registrarAlimentacion(alimentacion));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
