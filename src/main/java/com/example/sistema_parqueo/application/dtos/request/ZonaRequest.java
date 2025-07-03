package com.example.sistema_parqueo.application.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZonaRequest {
    @NotBlank
    @Size(max=20)
    private String nombre;

    @Size(max=250)
    private String descripcion;

    @NotNull
    @Min(1)
    private Integer totalEspacios;
}
