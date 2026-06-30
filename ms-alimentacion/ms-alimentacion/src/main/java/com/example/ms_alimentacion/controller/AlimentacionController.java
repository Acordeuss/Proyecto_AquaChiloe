package com.example.ms_alimentacion.controller;

import com.example.ms_alimentacion.model.Alimentacion;
import com.example.ms_alimentacion.service.AlimentacionService;
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
    public ResponseEntity<Alimentacion> crear(@Valid @RequestBody Alimentacion alimentacion) {
        return ResponseEntity.status(201).body(service.registrarAlimentacion(alimentacion));
    }
}