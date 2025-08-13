package com.shujaa.parking_system.application.dtos.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequest {
    @NotBlank
    private String nombres;
    @NotBlank
    private String apePaterno;
    @NotBlank
    private String apeMaterno;
    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe contener exactamente 8 dígitos numéricos")
    private String dni;
    @NotBlank
    @Size(min = 9, max = 9)
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe contener exactamente 9 dígitos numéricos")
    private String telefono;

    @NotBlank
    private String direccion;
    @NotBlank
    @Email
    private String correo;
} 