package com.shujaa.parking_system.application.dtos.mapper;

import com.shujaa.parking_system.application.dtos.request.ZonaRequest;
import com.shujaa.parking_system.application.dtos.response.ZonaResponse;
import com.shujaa.parking_system.domain.entity.Zona;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZonaMapper {
    ZonaResponse toResponse(Zona zona);
    Zona toEntity(ZonaRequest request);
} 