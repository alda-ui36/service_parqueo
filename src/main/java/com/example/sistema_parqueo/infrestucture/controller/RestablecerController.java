package com.example.sistema_parqueo.infrestucture.controller;

import com.example.sistema_parqueo.application.dtos.request.RecuperarPasswordRequest;
import com.example.sistema_parqueo.application.dtos.request.ResetPasswordRequest;
import com.example.sistema_parqueo.application.dtos.response.ApiResponse;
import com.example.sistema_parqueo.domain.port.input.IPasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restablecer")
@RequiredArgsConstructor
public class RestablecerController {
    private final IPasswordResetService passwordResetService;

    @GetMapping("/solicitar-codigo")
    public ResponseEntity<ApiResponse<Void>> solicitarCodigo(@RequestParam String email){
        passwordResetService.solicitarCodigo(new RecuperarPasswordRequest(email));
        return ResponseEntity.ok(ApiResponse.success(null,"codigo de restablecimiento enviado al correo"));
    }

    @PostMapping("/resetear")
    public ResponseEntity<ApiResponse<Void>> resetearPassword(@Valid @RequestBody ResetPasswordRequest request){
        passwordResetService.restablecerPassword(request);
        return ResponseEntity.ok(ApiResponse.success(null,"contraseña restablecida con éxito"));
    }
}
