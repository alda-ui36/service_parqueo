package com.shujaa.parking_system.application.dtos.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {
    @NotNull
    private String email;
    @NotBlank
    private String codigo;
    @NotBlank
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=\\S+$).{6,}$", message = "La contraseña debe tener al menos una mayúscula y no contener espacios")
    private String nuevaPassword;
} 