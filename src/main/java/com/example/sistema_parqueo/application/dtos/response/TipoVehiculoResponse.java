package com.example.sistema_parqueo.application.dtos.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoVehiculoResponse {
    private Integer id;
    private String nombre;
    private BigDecimal tarifaHora;
    private String descripcion;
    private Boolean estado;
} 