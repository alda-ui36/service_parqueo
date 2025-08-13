package com.shujaa.parking_system.application.service;

import com.shujaa.parking_system.application.dtos.request.*;
import com.shujaa.parking_system.application.dtos.response.*;
import com.shujaa.parking_system.infrestucture.security.JwtUtils;
import com.shujaa.parking_system.domain.entity.Rol;
import com.shujaa.parking_system.domain.entity.Usuario;
import com.shujaa.parking_system.domain.port.input.IAuthService;
import com.shujaa.parking_system.domain.port.output.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shujaa.parking_system.application.dtos.mapper.AuthMapper;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthMapper authMapper;

    @Override
    public AuthResponse login(LoginRequest request) {
        var usuario = usuarioRepository.findByUsernameOrEmail(request.getLogin(), request.getLogin())
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña incorrectos"));
        if (usuario.getEstado() != null && !usuario.getEstado()) {
            throw new IllegalArgumentException("Usuario deshabilitado");
        }
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos");
        }
        return buildAuthResponse(usuario);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Refresh token inválido o expirado");
        }
        var claims = jwtUtils.getClaims(refreshToken);
        Integer userId = claims.get("userId", Integer.class);
        var usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return buildAuthResponse(usuario);
    }

    private AuthResponse buildAuthResponse(Usuario usuario) {
        var roles = usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet());
        var accessToken = jwtUtils.generateAccessToken(usuario.getId(), usuario.getUsername(), roles);
        var refreshToken = jwtUtils.generateRefreshToken(usuario.getId(), usuario.getUsername());
        return authMapper.toAuthResponse(usuario, accessToken, refreshToken);
    }
} 