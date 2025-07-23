package com.shujaa.parking_system.application.dtos.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroEntradaRequest {

    private String dni;
    private String nombres;
    private String apePaterno;
    private String apeMaterno;
    private String telefono;
    private String correo;
    private String direccion;


    private String placa;
    private String marca;
    private String color;
    private Integer idTipoVehiculo;

    private Integer idEspacio;
    private BigDecimal tarifaHora;
}
