package com.aquachiloe.lotes.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "lotes")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String especie; // Ej: Salmón Atlántico, Trucha Arcoíris

    @Column(nullable = false)
    private String origenOvas;

    @Column(nullable = false)
    private Integer cantidadPeces;

    @Column(nullable = false)
    private Double pesoPromedioInicial; // en kg

    @Column(nullable = false)
    private Long jaulaId; // Relación lógica por ID (Base de datos independiente)

    private LocalDate fechaIngreso;

    @Column(nullable = false)
    private boolean activo = true;

    // Constructores, Getters y Setters
    public Lote() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getOrigenOvas() { return origenOvas; }
    public void setOrigenOvas(String origenOvas) { this.origenOvas = origenOvas; }
    public Integer getCantidadPeces() { return cantidadPeces; }
    public void setCantidadPeces(Integer cantidadPeces) { this.cantidadPeces = cantidadPeces; }
    public Double getPesoPromedioInicial() { return pesoPromedioInicial; }
    public void setPesoPromedioInicial(Double pesoPromedioInicial) { this.pesoPromedioInicial = pesoPromedioInicial; }
    public Long getJaulaId() { return jaulaId; }
    public void setJaulaId(Long jaulaId) { this.jaulaId = jaulaId; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}