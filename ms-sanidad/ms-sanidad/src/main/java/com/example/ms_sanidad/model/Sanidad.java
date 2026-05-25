package com.example.ms_sanidad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tratamientos_sanitarios")
public class Sanidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de jaula es obligatorio")
    private Long jaulaId;

    @NotBlank(message = "El nombre del medicamento es obligatorio")
    private String medicamento;

    @NotNull(message = "La fecha de aplicación es obligatoria")
    private LocalDateTime fechaAplicacion;

    @Min(value = 0, message = "Los días de carencia no pueden ser negativos")
    @NotNull(message = "Los días de carencia son obligatorios")
    private Integer diasCarencia;

    private String veterinarioResponsable;
    private String observaciones;

    public Sanidad() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getJaulaId() { return jaulaId; }
    public void setJaulaId(Long jaulaId) { this.jaulaId = jaulaId; }
    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }
    public LocalDateTime getFechaAplicacion() { return fechaAplicacion; }
    public void setFechaAplicacion(LocalDateTime fechaAplicacion) { this.fechaAplicacion = fechaAplicacion; }
    public Integer getDiasCarencia() { return diasCarencia; }
    public void setDiasCarencia(Integer diasCarencia) { this.diasCarencia = diasCarencia; }
    public String getVeterinarioResponsable() { return veterinarioResponsable; }
    public void setVeterinarioResponsable(String veterinarioResponsable) { this.veterinarioResponsable = veterinarioResponsable; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
