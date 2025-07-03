package com.example.sistema_parqueo.application.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspacioResponse {
    private Integer id;
    private Integer zonaId;
    private String zonaNombre;
    private Integer tipoVehiculoId;
    private String tipoVehiculoNombre;
    private String numeroEspacio;
    private Boolean estado;
    private Boolean ocupado;
}
