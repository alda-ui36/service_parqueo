package com.shujaa.parking_system.application.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecuperarPasswordRequest {
    @NotBlank
    @Email
    private String email;
} 