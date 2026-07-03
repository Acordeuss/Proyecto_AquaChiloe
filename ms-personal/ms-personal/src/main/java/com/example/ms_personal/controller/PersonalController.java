package com.example.ms_personal.controller;

import com.example.ms_personal.model.Trabajador;
import com.example.ms_personal.service.PersonalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/personal")
public class PersonalController {

    @Autowired
    private PersonalService service;

    @GetMapping("/")
    @Operation(summary = "Listar trabajadores", description = "Obtiene todos los trabajadores registrados.")
    @ApiResponse(responseCode = "200", description = "Listado de trabajadores")
    public List<Trabajador> listar() {
        return service.listarTodos();
    }

    @PostMapping("/")
    @Operation(summary = "Registrar trabajador", description = "Crea un trabajador con RUT, nombre, turno y cargo opcional.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trabajador registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o error de persistencia")
    })
    public Trabajador crear(@Valid @RequestBody Trabajador t) {
        return service.registrarTrabajador(t);
    }
}
