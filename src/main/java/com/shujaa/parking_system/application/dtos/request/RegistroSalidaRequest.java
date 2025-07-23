package com.shujaa.parking_system.application.dtos.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroSalidaRequest {
    private String ticket;
    private BigDecimal montoTotal;
    private Integer idMetodoPago;
}
