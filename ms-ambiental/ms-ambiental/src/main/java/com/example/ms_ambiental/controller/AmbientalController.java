package com.example.ms_ambiental.controller;

import com.example.ms_ambiental.model.LecturaAmbiental;
import com.example.ms_ambiental.service.AmbientalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Registrar lectura ambiental", description = "Registra una lectura de oxigeno, temperatura y salinidad.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lectura registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o error de persistencia")
    })
    public ResponseEntity<LecturaAmbiental> recibirLectura(@Valid @RequestBody LecturaAmbiental lectura) {
        return ResponseEntity.status(201).body(service.registrarLectura(lectura));
    }

    @GetMapping("/status-critico/{centroId}")
    @Operation(summary = "Consultar alerta critica", description = "Retorna si la ultima lectura del centro tiene alerta critica.")
    @ApiResponse(responseCode = "200", description = "Estado de alerta retornado")
    public ResponseEntity<Boolean> verificarAlerta(@Parameter(description = "ID del centro") @PathVariable Long centroId) {
        return ResponseEntity.ok(service.hayAlertaActiva(centroId));
    }
}
