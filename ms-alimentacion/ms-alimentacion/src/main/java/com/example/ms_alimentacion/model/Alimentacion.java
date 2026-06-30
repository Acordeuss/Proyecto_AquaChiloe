package com.example.ms_alimentacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "registros_alimentacion")
public class Alimentacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de jaula es obligatorio")
    private Long jaulaId;

    @NotNull(message = "La cantidad de alimento es obligatoria")
    @Min(value = 1, message = "La cantidad de alimento debe ser mayor a 0 kg")
    private Double cantidadAlimentoKilos;

    private LocalDate fechaRegistro = LocalDate.now();

    public Alimentacion() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getJaulaId() { return jaulaId; }

    public void setJaulaId(Long jaulaId) { this.jaulaId = jaulaId; }

    public Double getCantidadAlimentoKilos() { return cantidadAlimentoKilos; }

    public void setCantidadAlimentoKilos(Double cantidadAlimentoKilos) { this.cantidadAlimentoKilos = cantidadAlimentoKilos; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }

    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}