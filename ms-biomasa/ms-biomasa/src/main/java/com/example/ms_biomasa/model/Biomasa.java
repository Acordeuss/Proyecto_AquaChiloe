package com.example.ms_biomasa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "registros_biomasa")
public class Biomasa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de jaula es requerido")
    private Long jaulaId;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    @Min(value = 1, message = "El peso promedio debe ser positivo")
    private Double pesoPromedioGramos;

    private Integer cantidadPeces;

    private LocalDate fechaMuestreo = LocalDate.now();

    public Biomasa() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getJaulaId() { return jaulaId; }

    public void setJaulaId(Long jaulaId) { this.jaulaId = jaulaId; }

    public String getEspecie() { return especie; }

    public void setEspecie(String especie) { this.especie = especie; }

    public Double getPesoPromedioGramos() { return pesoPromedioGramos; }

    public void setPesoPromedioGramos(Double pesoPromedioGramos) { this.pesoPromedioGramos = pesoPromedioGramos;
    }
    public Integer getCantidadPeces() { return cantidadPeces; }

    public void setCantidadPeces(Integer cantidadPeces) { this.cantidadPeces = cantidadPeces; }

    public LocalDate getFechaMuestreo() { return fechaMuestreo; }

    public void setFechaMuestreo(LocalDate fechaMuestreo) { this.fechaMuestreo = fechaMuestreo; }
}