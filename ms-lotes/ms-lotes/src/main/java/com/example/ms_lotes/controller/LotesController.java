package com.example.ms_lotes.controller;

import com.example.ms_lotes.model.Lote;
import com.example.ms_lotes.service.LotesService;
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
    public ResponseEntity<Lote> registrarLote(@Valid @RequestBody Lote lote) {
        return ResponseEntity.status(201).body(service.crearLote(lote));
    }

    @GetMapping("/cantidad-peces/{jaulaId}")
    public ResponseEntity<Integer> obtenerCantidadPeces(@PathVariable Long jaulaId) {
        return ResponseEntity.ok(service.obtenerPecesPorJaula(jaulaId));
    }
}