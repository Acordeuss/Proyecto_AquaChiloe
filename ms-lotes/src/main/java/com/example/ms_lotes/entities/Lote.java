package com.example.ms_lotes.entities;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoLote; // Ej: LOTE-2026-001
    private String especie;    // Salmón Atlántico, Trucha, etc.
    private Integer cantidadPeces;
    private Double pesoPromedio; // en kg
    private Long centroId;     // FK lógica al ms-centros
    private Long jaulaId;      // FK lógica al ms-centros
}