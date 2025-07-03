package com.example.sistema_parqueo.application.dtos.mapper;

import com.example.sistema_parqueo.application.dtos.request.ZonaRequest;
import com.example.sistema_parqueo.application.dtos.response.ZonaResponse;
import com.example.sistema_parqueo.domain.entity.Zona;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZonaMapper {
    ZonaResponse toResponse(Zona zona);
    Zona toEntity(ZonaRequest request);
}
