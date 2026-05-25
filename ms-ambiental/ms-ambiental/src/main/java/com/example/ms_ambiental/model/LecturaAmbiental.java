package com.example.ms_ambiental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lecturas_ambientales")
public class LecturaAmbiental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del centro es obligatorio")
    private Long centroId;

    @NotBlank(message = "El ID del sensor es obligatorio")
    private String sensorId;

    @NotNull(message = "El nivel de oxígeno es obligatorio")
    private Double oxigeno;

    @NotNull(message = "La temperatura es obligatoria")
    private Double temperatura;

    @NotNull(message = "La salinidad es obligatoria")
    private Double salinidad;

    private LocalDateTime fechaLectura = LocalDateTime.now();
    private Boolean alertaCritica = false;

    public LecturaAmbiental() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCentroId() { return centroId; }
    public void setCentroId(Long centroId) { this.centroId = centroId; }
    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
    public Double getOxigeno() { return oxigeno; }
    public void setOxigeno(Double oxigeno) { this.oxigeno = oxigeno; }
    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }
    public Double getSalinidad() { return salinidad; }
    public void setSalinidad(Double salinidad) { this.salinidad = salinidad; }
    public LocalDateTime getFechaLectura() { return fechaLectura; }
    public void setFechaLectura(LocalDateTime fechaLectura) { this.fechaLectura = fechaLectura; }
    public Boolean getAlertaCritica() { return alertaCritica; }
    public void setAlertaCritica(Boolean alertaCritica) { this.alertaCritica = alertaCritica; }
}
