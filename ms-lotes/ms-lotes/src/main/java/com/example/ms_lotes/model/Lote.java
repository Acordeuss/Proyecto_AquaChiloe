package com.example.ms_lotes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "lotes")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código de lote es obligatorio")
    private String codigoLote;

    @NotNull(message = "El ID de la jaula es obligatorio")
    private Long jaulaId;

    @Min(value = 1, message = "La cantidad inicial de peces debe ser mayor a 0")
    @NotNull(message = "La cantidad de peces es obligatoria")
    private Integer cantidadPeces;

    private LocalDate fechaIngreso = LocalDate.now();

    public Lote() {
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String codigoLote) { this.codigoLote = codigoLote; }
    public Long getJaulaId() { return jaulaId; }
    public void setJaulaId(Long jaulaId) { this.jaulaId = jaulaId; }
    public Integer getCantidadPeces() { return cantidadPeces; }
    public void setCantidadPeces(Integer cantidadPeces) { this.cantidadPeces = cantidadPeces; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
}