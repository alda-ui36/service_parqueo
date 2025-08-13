package com.shujaa.parking_system.infrestucture.controller;

import com.shujaa.parking_system.application.dtos.request.RecuperarPasswordRequest;
import com.shujaa.parking_system.application.dtos.request.ResetPasswordRequest;
import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import com.shujaa.parking_system.domain.port.input.IPasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordResetController {
    private final IPasswordResetService passwordResetService;

    @GetMapping("/solicitar-codigo")
    public ResponseEntity<ApiResponse<Void>> solicitarCodigo(@RequestParam String email) {
        passwordResetService.solicitarCodigo(new RecuperarPasswordRequest(email));
        return ResponseEntity.ok(ApiResponse.success(null, "Codigo enviado correctamente"));
    }

    @PostMapping("/resetear")
    public ResponseEntity<ApiResponse<Void>> resetearPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordResetService.resetearPassword(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Contraseña restablecida correctamente"));
    }
} 