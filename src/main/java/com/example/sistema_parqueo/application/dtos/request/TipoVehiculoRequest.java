package com.example.sistema_parqueo.application.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoVehiculoRequest {
    @NotBlank
    @Size(max = 50)
    private String nombre;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false, message = "La tarifa debe ser mayor a 0")
    private BigDecimal tarifaHora;

    @Size(max = 250)
    private String descripcion;
} 