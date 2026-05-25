package com.example.ms_centros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "jaulas")
public class Jaula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código de la jaula es obligatorio")
    private String codigoJaula;

    @NotNull(message = "El ID del centro es obligatorio")
    private Long centroId;

    private Boolean activa = true;

    public Jaula() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCodigoJaula() { return codigoJaula; }

    public void setCodigoJaula(String codigoJaula) { this.codigoJaula = codigoJaula; }

    public Long getCentroId() { return centroId;}

    public void setCentroId(Long centroId) { this.centroId = centroId; }

    public Boolean getActiva() { return activa; }

    public void setActiva(Boolean activa) { this.activa = activa; }
}
