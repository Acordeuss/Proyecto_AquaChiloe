package com.aquachiloe.lotes.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LoteRequestDTO {
    @NotBlank(message = "La especie no puede estar vacía") private String especie;
    @NotBlank(message = "El origen de las ovas es obligatorio") private String origenOvas;
    @Min(value = 1, message = "La cantidad debe ser mayor a 0") @NotNull private Integer cantidadPeces;
    @Min(value = 0, message = "El peso no puede ser negativo") @NotNull private Double pesoPromedioInicial;
    @NotNull private Long jaulaId;
    private LocalDate fechaIngreso;
}
