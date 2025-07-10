package com.example.sistema_parqueo.application.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoResponse {
    private Integer id;
    private String placa;
    private  String marca;
    private String color;
    private  String tipoVehiculo;
    private  Integer idTipoVehiculo;
    private Integer idCliente;
    private String dniCliente;
    private String nombresCliente;
    private String apeParternoCliente;
    private String apeMaternoCliente;
    private Boolean estado;
}
