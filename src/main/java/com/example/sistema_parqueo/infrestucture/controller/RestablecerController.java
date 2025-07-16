package com.example.sistema_parqueo.infrestucture.controller;

import com.example.sistema_parqueo.application.dtos.request.RecuperarPasswordRequest;
import com.example.sistema_parqueo.application.dtos.response.ApiResponse;
import com.example.sistema_parqueo.domain.port.input.IPasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
