package com.shujaa.parking_system.application.dtos.response;

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
    private String numeroEspacio;
    private Boolean ocupado;
    private Boolean estado;
    private Integer tipoVehiculoId;
    private String tipoVehiculoNombre;
} 