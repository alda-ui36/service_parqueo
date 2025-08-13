package com.shujaa.parking_system.application.dtos.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroParqueoResponse {
    private Integer id;
    private String ticket;
    // Cliente
    private Integer idCliente;
    private String nombresCliente;
    private String apePaternoCliente;
    private String apeMaternoCliente;
    private String dniCliente;
    private String telefonoCliente;
    private String correoCliente;
    // Vehículo
    private Integer idVehiculo;
    private String placaVehiculo;
    private String marcaVehiculo;
    private String colorVehiculo;
    private String tipoVehiculo;
    private Integer idTipoVehiculo;
    // Espacio
    private Integer idEspacio;
    private String numeroEspacio;
    private String zonaNombre;
    // Método de pago
    private Integer idMetodoPago;
    private String nombreMetodoPago;
    // Fechas y montos
    private LocalDateTime horaIngreso;
    private LocalDateTime horaSalida;
    private BigDecimal tarifaHora;
    private BigDecimal montoTotal;
    private String estadoPago;
    // Auditoría
    private Integer idCreadoPor;
    private String nombreCreadoPor;
    private Integer idActualizadoPor;
    private String nombreActualizadoPor;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
} 