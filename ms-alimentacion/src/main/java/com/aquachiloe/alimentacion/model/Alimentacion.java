package com.aquachiloe.alimentacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_alimentacion")
public class Alimentacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de jaula es obligatorio")
    private Long jaulaId;

    @Min(value = 1, message = "La cantidad de alimento debe ser positiva")
    private Double cantidadAlimentoKilos;

    private String tipoAlimento;

    private LocalDateTime fechaAlimentacion = LocalDateTime.now();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getJaulaId() { return jaulaId; }
    public void setJaulaId(Long jaulaId) { this.jaulaId = jaulaId; }
    public Double getCantidadAlimentoKilos() { return cantidadAlimentoKilos; }
    public void setCantidadAlimentoKilos(Double cantidadAlimentoKilos) { this.cantidadAlimentoKilos = cantidadAlimentoKilos; }
    public String getTipoAlimento() { return tipoAlimento; }
    public void setTipoAlimento(String tipoAlimento) { this.tipoAlimento = tipoAlimento; }
    public LocalDateTime getFechaAlimentacion() { return fechaAlimentacion; }
    public void setFechaAlimentacion(LocalDateTime fechaAlimentacion) { this.fechaAlimentacion = fechaAlimentacion; }
}
