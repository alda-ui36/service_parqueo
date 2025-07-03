package com.example.sistema_parqueo.application.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
