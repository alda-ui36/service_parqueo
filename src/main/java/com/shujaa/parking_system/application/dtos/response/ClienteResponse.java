package com.shujaa.parking_system.application.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse {
    private Integer id;
    private String nombres;
    private String apePaterno;
    private String apeMaterno;
    private String dni;
    private String telefono;
    private String direccion;
    private String correo;
    private Boolean estado;
    private Integer distritoId;
    private String origen;
}