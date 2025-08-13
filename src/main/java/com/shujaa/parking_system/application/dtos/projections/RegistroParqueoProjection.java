package com.shujaa.parking_system.application.dtos.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RegistroParqueoProjection {
    String getTicket();

    LocalDateTime getHoraIngreso();

    BigDecimal getTarifaHora();

    BigDecimal getMontoTotal();

    String getEstadoPago();

    // Cliente
    String getNombres();

    String getApePaterno();

    String getApeMaterno();

    String getDni();

    String getTelefono();

    // Vehículo
    String getPlaca();

    String getMarca();

    String getColor();

    String getTipoVehiculo();
    String getEmail();

    // Espacio
    String getZonaNombre();

    String getNumeroEspacio();
}