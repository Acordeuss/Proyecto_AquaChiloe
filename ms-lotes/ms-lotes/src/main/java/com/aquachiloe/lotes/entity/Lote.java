package com.aquachiloe.lotes.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lotes")
public class Lote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String especie;
    @Column(nullable = false) private String origenOvas;
    @Column(nullable = false) private Integer cantidadPeces;
    @Column(nullable = false) private Double pesoPromedioInicial;
    @Column(nullable = false) private Long jaulaId;
    private LocalDate fechaIngreso;
    @Column(nullable = false) private boolean activo = true;
}
