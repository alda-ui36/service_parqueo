package com.example.sistema_parqueo.application.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {
    @NotNull
    private String email;
    @NotBlank
    private String codigo;
    @NotBlank
    @Size(min = 8, message = "la contraseña debe tener al menos 8 caracteres")
    private String newPassword;
}
