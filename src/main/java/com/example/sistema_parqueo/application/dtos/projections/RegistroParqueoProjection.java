package com.example.sistema_parqueo.application.dtos.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RegistroParqueoProjection {
    String getTicket();
    LocalDateTime getHoraIngreso();
    BigDecimal tarifaHora();
    BigDecimal getMontoTotal();
    String getEstadoPago();


}
