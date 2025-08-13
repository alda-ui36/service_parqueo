package com.shujaa.parking_system.application.dtos.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZonaRequest {
    @NotBlank
    @Size(max = 50)
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    @NotNull
    @Min(1)
    private Integer totalEspacios;
} 