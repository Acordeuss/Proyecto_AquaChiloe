package com.example.ms_lotes.controller;

import com.example.ms_lotes.model.Lote;
import com.example.ms_lotes.service.LotesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lotes")
public class LotesController {

    @Autowired
    private LotesService service;

    @PostMapping("/")
    @Operation(summary = "Registrar lote", description = "Crea un lote de peces validando previamente la jaula en ms-centros.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lote registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Jaula invalida, datos invalidos o error de persistencia")
    })
    public ResponseEntity<Lote> registrarLote(@Valid @RequestBody Lote lote) {
        return ResponseEntity.status(201).body(service.crearLote(lote));
    }

    @GetMapping("/cantidad-peces/{jaulaId}")
    @Operation(summary = "Consultar cantidad de peces", description = "Retorna la cantidad de peces del ultimo lote asociado a una jaula.")
    @ApiResponse(responseCode = "200", description = "Cantidad de peces encontrada")
    public ResponseEntity<Integer> obtenerCantidadPeces(@Parameter(description = "ID de la jaula") @PathVariable Long jaulaId) {
        return ResponseEntity.ok(service.obtenerPecesPorJaula(jaulaId));
    }
}
