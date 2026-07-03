package com.example.ms_sanidad.controller;

import com.example.ms_sanidad.model.Sanidad;
import com.example.ms_sanidad.service.SanidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Registrar tratamiento sanitario", description = "Registra un tratamiento sanitario aplicado a una jaula.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tratamiento registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o error de persistencia")
    })
    public ResponseEntity<Sanidad> crearTratamiento(@Valid @RequestBody Sanidad sanidad) {
        return ResponseEntity.status(201).body(service.registrarTratamiento(sanidad));
    }

    @GetMapping("/verificar-bloqueo/{jaulaId}")
    @Operation(summary = "Verificar bloqueo sanitario", description = "Indica si una jaula sigue dentro de periodo de carencia.")
    @ApiResponse(responseCode = "200", description = "Estado de bloqueo sanitario")
    public ResponseEntity<Boolean> comprobarCarencia(@Parameter(description = "ID de la jaula") @PathVariable Long jaulaId) {
        return ResponseEntity.ok(service.verificarBloqueoSantario(jaulaId));
    }
}
