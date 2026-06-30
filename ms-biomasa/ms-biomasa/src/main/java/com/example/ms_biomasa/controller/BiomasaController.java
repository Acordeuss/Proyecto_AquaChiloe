package com.example.ms_biomasa.controller;

import com.example.ms_biomasa.model.Biomasa;
import com.example.ms_biomasa.service.BiomasaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/biomasa")
public class BiomasaController {

    @Autowired
    private BiomasaService service;

    @PostMapping("/")
    public ResponseEntity<Biomasa> crearMuestreo(@Valid @RequestBody Biomasa biomasa) {
        return ResponseEntity.status(201).body(service.registrarMuestreo(biomasa));
    }

    @GetMapping("/total/{jaulaId}")
    public ResponseEntity<Double> obtenerTotalKilos(@PathVariable Long jaulaId) {
        return ResponseEntity.ok(service.calcularBiomasaTotalKilos(jaulaId));
    }
}
