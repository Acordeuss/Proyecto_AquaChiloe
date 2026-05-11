package com.aquachiloe.biomasa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registro_biomasa")
public class Biomasa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_biomasa")
    private Long idRegistroBiomasa;

    @NotNull(message = "El ID de jaula es obligatorio")
    @Column(name = "id_jaula")
    private Long idJaula;

    @Min(value = 1, message = "El peso debe ser positivo")
    @Column(name = "peso_promedio")
    private Double pesoPromedio;

    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Column(name = "cantidad_peces")
    private Integer cantidadPeces;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    // Ejecuta esto automáticamente antes de guardar en la BD
    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getIdRegistroBiomasa() { return idRegistroBiomasa; }
    public void setIdRegistroBiomasa(Long idRegistroBiomasa) { this.idRegistroBiomasa = idRegistroBiomasa; }

    public Long getIdJaula() { return idJaula; }
    public void setIdJaula(Long idJaula) { this.idJaula = idJaula; }

    public Double getPesoPromedio() { return pesoPromedio; }
    public void setPesoPromedio(Double pesoPromedio) { this.pesoPromedio = pesoPromedio; }

    public Integer getCantidadPeces() { return cantidadPeces; }
    public void setCantidadPeces(Integer cantidadPeces) { this.cantidadPeces = cantidadPeces; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
