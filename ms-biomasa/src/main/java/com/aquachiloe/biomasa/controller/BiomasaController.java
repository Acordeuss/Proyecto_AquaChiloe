package com.aquachiloe.biomasa.controller;

import com.aquachiloe.biomasa.model.Biomasa;
import com.aquachiloe.biomasa.service.BiomasaService;
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
    public ResponseEntity<Biomasa> crear(@Valid @RequestBody Biomasa biomasa) {
        return ResponseEntity.status(201).body(service.registrar(biomasa));
    }

    @GetMapping("/total/{idJaula}")
    public ResponseEntity<Double> obtenerTotal(@PathVariable Long idJaula) {
        return ResponseEntity.ok(service.calcularBiomasaTotalKilos(idJaula));
    }
}
