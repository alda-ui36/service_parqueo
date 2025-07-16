package com.example.sistema_parqueo.domain.port.input;

import com.example.sistema_parqueo.application.dtos.request.LoginRequest;
import com.example.sistema_parqueo.application.dtos.response.AuthResponse;

public interface IAuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(String refreshToken);
}
