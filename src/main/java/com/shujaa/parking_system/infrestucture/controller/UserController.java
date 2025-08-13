package com.shujaa.parking_system.infrestucture.controller;

import com.shujaa.parking_system.application.dtos.request.RegisterRequest;
import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import com.shujaa.parking_system.domain.port.input.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Usuario registrado correctamente"));
    }
}