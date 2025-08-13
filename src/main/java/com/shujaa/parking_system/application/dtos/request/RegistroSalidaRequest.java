package com.shujaa.parking_system.application.dtos.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroSalidaRequest {
    @NotBlank(message = "El número de ticket es obligatorio")
    @Pattern(regexp = "^[A-Z0-9]{8,12}$", message = "El ticket debe tener entre 8 y 12 caracteres alfanuméricos")
    private String ticket;

    @NotNull(message = "El monto total es obligatorio")
    @Positive(message = "El monto total debe ser un valor positivo")
    private BigDecimal montoTotal;

    @NotNull(message = "El ID del método de pago es obligatorio")
    @Positive(message = "El ID del método de pago debe ser un valor positivo")
    private Integer idMetodoPago;
}