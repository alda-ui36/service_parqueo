package com.example.sistema_parqueo.application.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 8,max = 8)
    @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe tener 8 dígitos y solo contener números")
    private String dni;

    @NotBlank
    @Size(min = 9,max = 9)
    @Pattern(regexp = "^[0-9]{9}$", message = "El Telefono debe tener 8 dígitos y solo contener números")
    private String telefono;

    @NotBlank
    private String direccion;

    @NotBlank
    @Email
    private String correo;
}
