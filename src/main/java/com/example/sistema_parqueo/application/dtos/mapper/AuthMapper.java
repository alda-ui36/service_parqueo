package com.example.sistema_parqueo.application.dtos.mapper;

import com.example.sistema_parqueo.application.dtos.response.AuthResponse;
import com.example.sistema_parqueo.domain.entity.Rol;
import com.example.sistema_parqueo.domain.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "accessToken",source = "accessToken")
    @Mapping(target = "refreshToken",source = "refreshToken")
    @Mapping(target = "userId",source = "usuario.id")
    @Mapping(target = "username",source = "usuario.username")
    @Mapping(target = "email",source = "usuario.email")
    @Mapping(target = "roles", expression = "java(mapRoles(usuario))")
    AuthResponse toAuthResponse(Usuario usuario, String accessToken, String refreshToken);

    default Set<String> mapRoles(Usuario usuario){
        if(usuario.getRoles() == null) return null;
        return usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet());
    }
}
