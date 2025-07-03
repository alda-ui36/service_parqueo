package com.example.sistema_parqueo.application.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZonaResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private Integer totalEspacios;
}
