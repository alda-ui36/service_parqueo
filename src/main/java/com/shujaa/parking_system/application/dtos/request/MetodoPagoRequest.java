package com.shujaa.parking_system.application.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetodoPagoRequest {
    @NotBlank
    @Size(max = 50)
    private String nombre;
} 