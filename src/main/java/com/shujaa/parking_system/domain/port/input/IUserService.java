package com.shujaa.parking_system.domain.port.input;

import com.shujaa.parking_system.application.dtos.request.RegisterRequest;

public interface IUserService {
    void register(RegisterRequest request);
}