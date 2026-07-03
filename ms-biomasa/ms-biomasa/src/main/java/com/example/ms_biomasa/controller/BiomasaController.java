package com.example.ms_biomasa.controller;

import com.example.ms_biomasa.model.Biomasa;
import com.example.ms_biomasa.service.BiomasaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Registrar muestreo de biomasa", description = "Registra un muestreo y consulta la cantidad oficial de peces en ms-lotes.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Muestreo registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o error de persistencia")
    })
    public ResponseEntity<Biomasa> crearMuestreo(@Valid @RequestBody Biomasa biomasa) {
        return ResponseEntity.status(201).body(service.registrarMuestreo(biomasa));
    }

    @GetMapping("/total/{jaulaId}")
    @Operation(summary = "Calcular biomasa total", description = "Calcula kilos totales usando el ultimo muestreo de una jaula.")
    @ApiResponse(responseCode = "200", description = "Biomasa total en kilos")
    public ResponseEntity<Double> obtenerTotalKilos(@Parameter(description = "ID de la jaula") @PathVariable Long jaulaId) {
        return ResponseEntity.ok(service.calcularBiomasaTotalKilos(jaulaId));
    }
}
