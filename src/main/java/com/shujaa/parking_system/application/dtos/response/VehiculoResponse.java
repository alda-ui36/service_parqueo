package com.shujaa.parking_system.application.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoResponse {
    private Integer id;
    private String placa;
    private String marca;
    private String color;
    private String tipoVehiculo;
    private Integer idTipoVehiculo;
    private Integer idCliente;
    private String dniCliente;
    private String nombresCliente;
    private String apePaternoCliente;
    private String apeMaternoCliente;
    private Boolean estado;
} 