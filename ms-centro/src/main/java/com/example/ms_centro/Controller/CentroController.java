package com.example.mscentro.controller;

import com.example.mscentro.model.Centro;
import com.example.mscentro.service.CentroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/centros")
public class CentroController {

    @Autowired
    private CentroService service;

    @GetMapping("/")
    public List<Centro> listar() {
        return service.obtenerTodos();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Centro centro) {
        try {
            Centro nuevo = service.guardar(centro);
            return ResponseEntity.status(201).body(nuevo); // Código 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Código 400 Bad Request
        }
    }
}