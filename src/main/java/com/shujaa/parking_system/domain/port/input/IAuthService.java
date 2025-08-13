package com.shujaa.parking_system.domain.port.input;

import com.shujaa.parking_system.application.dtos.request.*;
import com.shujaa.parking_system.application.dtos.response.AuthResponse;

public interface IAuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(String refreshToken);
} 