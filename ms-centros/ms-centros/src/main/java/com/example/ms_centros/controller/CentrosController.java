package com.example.ms_centros.controller;

import com.example.ms_centros.model.Jaula;
import com.example.ms_centros.service.CentrosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Registrar jaula", description = "Crea una jaula asociada a un centro acuicola.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Jaula registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o error de persistencia")
    })
    public ResponseEntity<Jaula> crearJaula(@Valid @RequestBody Jaula jaula) {
        return ResponseEntity.status(201).body(service.registrarJaula(jaula));
    }

    @GetMapping("/jaulas/{id}/verificar")
    @Operation(summary = "Verificar jaula", description = "Indica si una jaula existe y se encuentra activa.")
    @ApiResponse(responseCode = "200", description = "Resultado de la verificacion")
    public ResponseEntity<Boolean> verificarJaula(@Parameter(description = "ID de la jaula") @PathVariable Long id) {
        return ResponseEntity.ok(service.verificarExistenciaJaula(id));
    }
}
