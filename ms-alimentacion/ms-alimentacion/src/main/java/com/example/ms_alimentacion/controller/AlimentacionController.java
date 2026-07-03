package com.example.ms_alimentacion.controller;

import com.example.ms_alimentacion.model.Alimentacion;
import com.example.ms_alimentacion.service.AlimentacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Registrar alimentacion", description = "Registra alimento entregado a una jaula validando biomasa disponible.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alimentacion registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Biomasa insuficiente, exceso de racion o datos invalidos")
    })
    public ResponseEntity<Alimentacion> crear(@Valid @RequestBody Alimentacion alimentacion) {
        return ResponseEntity.status(201).body(service.registrarAlimentacion(alimentacion));
    }
}
