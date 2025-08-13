package com.shujaa.parking_system.domain.port.input;

import com.shujaa.parking_system.application.dtos.request.RecuperarPasswordRequest;
import com.shujaa.parking_system.application.dtos.request.ResetPasswordRequest;

public interface IPasswordResetService {
    void solicitarCodigo(RecuperarPasswordRequest request);
    void resetearPassword(ResetPasswordRequest request);
} 