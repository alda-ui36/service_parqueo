package com.example.sistema_parqueo.application.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetodoPagoResponse {
    private Integer id;
    private String nombre;
    private Boolean estado;
} 