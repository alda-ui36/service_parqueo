package com.shujaa.parking_system.application.dtos.mapper;

import com.shujaa.parking_system.application.dtos.response.AuthResponse;
import com.shujaa.parking_system.domain.entity.Rol;
import com.shujaa.parking_system.domain.entity.Usuario;
import org.mapstruct.*;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @Mapping(target = "accessToken", source = "accessToken")
    @Mapping(target = "refreshToken", source = "refreshToken")
    @Mapping(target = "userId", source = "usuario.id")
    @Mapping(target = "username", source = "usuario.username")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "roles", expression = "java(mapRoles(usuario))")
    AuthResponse toAuthResponse(Usuario usuario, String accessToken, String refreshToken);

    default Set<String> mapRoles(Usuario usuario) {
        if (usuario.getRoles() == null) return null;
        return usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet());
    }
} 