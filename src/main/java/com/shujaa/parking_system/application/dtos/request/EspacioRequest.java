package com.shujaa.parking_system.application.dtos.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspacioRequest {
    @NotNull
    private Integer zonaId;

    @NotBlank
    @Size(max = 10)
    private String numeroEspacio;

    @NotNull
    private Integer tipoVehiculoId;
} 