package com.shujaa.parking_system.application.service;

import com.shujaa.parking_system.application.dtos.mapper.AuthMapper;
import com.shujaa.parking_system.application.dtos.request.RegisterRequest;
import com.shujaa.parking_system.infrestucture.security.JwtUtils;
import com.shujaa.parking_system.domain.entity.Usuario;
import com.shujaa.parking_system.domain.port.output.IRolRepository;
import com.shujaa.parking_system.domain.port.input.IUserService;
import com.shujaa.parking_system.domain.port.output.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUsuarioRepository usuarioRepository;
    private final IRolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthMapper authMapper;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        usuarioRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail())
                .ifPresent(u -> {
                    if (u.getUsername().equals(request.getUsername())) {
                        throw new IllegalArgumentException("El username ya está registrado");
                    }
                    if (u.getEmail().equals(request.getEmail())) {
                        throw new IllegalArgumentException("El email ya está registrado");
                    }
                });

        var rol = rolRepository.findByNombre("USER")
                .orElseThrow(() -> new IllegalArgumentException("Rol por defecto no encontrado"));

        var usuario = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombreCompleto(request.getNombreCompleto())
                .creadoEn(java.time.LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rol))
                .build();
        usuarioRepository.save(usuario);
    }
}