package com.shujaa.parking_system.application.dtos.response;

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