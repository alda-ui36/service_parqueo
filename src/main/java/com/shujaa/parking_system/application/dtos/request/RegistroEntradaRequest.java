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

    // Datos del vehículo (opcionales si se proporciona idVehiculo)
    @Pattern(regexp = "^[A-Z0-9]{6,8}$", message = "La placa debe tener entre 6 y 8 caracteres alfanuméricos")
    private String placa;

    @NotBlank(message = "La marca es obligatoria si no se proporciona idVehiculo")
    private String marca;

    @NotBlank(message = "El color es obligatorio si no se proporciona idVehiculo")
    private String color;

    private Integer idTipoVehiculo;

    // Datos del cliente (opcionales si se proporciona idCliente)
    @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe tener exactamente 8 dígitos numéricos")
    private String dni;

    @NotBlank(message = "Los nombres son obligatorios si no se proporciona idCliente")
    private String nombres;

    @NotBlank(message = "El apellido paterno es obligatorio si no se proporciona idCliente")
    private String apePaterno;

    @NotBlank(message = "El apellido materno es obligatorio si no se proporciona idCliente")
    private String apeMaterno;

    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria si no se proporciona idCliente")
    private String direccion;

    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    // Datos del parqueo (siempre requeridos)
    @NotNull(message = "El ID del espacio es obligatorio")
    private Integer idEspacio;

    @NotNull(message = "La tarifa por hora es obligatoria")
    @Positive(message = "La tarifa por hora debe ser un valor positivo")
    private BigDecimal tarifaHora;
}