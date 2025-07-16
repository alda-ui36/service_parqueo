package com.example.sistema_parqueo.domain.port.input;

import com.example.sistema_parqueo.application.dtos.request.RecuperarPasswordRequest;
import com.example.sistema_parqueo.application.dtos.request.ResetPasswordRequest;

public interface IPasswordResetService {
    void solicitarCodigo(RecuperarPasswordRequest request);
    void restablecerPassword(ResetPasswordRequest request);
}
