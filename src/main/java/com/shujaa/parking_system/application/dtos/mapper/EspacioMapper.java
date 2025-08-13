package com.shujaa.parking_system.application.dtos.mapper;

import com.shujaa.parking_system.application.dtos.request.EspacioRequest;
import com.shujaa.parking_system.application.dtos.response.EspacioResponse;
import com.shujaa.parking_system.domain.entity.Espacio;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EspacioMapper {
    @Mapping(source = "zona.id", target = "zonaId")
    @Mapping(source = "zona.nombre", target = "zonaNombre")
    @Mapping(source = "tipoVehiculo.id", target = "tipoVehiculoId")
    @Mapping(source = "tipoVehiculo.nombre", target = "tipoVehiculoNombre")
    EspacioResponse toResponse(Espacio espacio);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "zona", ignore = true)
    @Mapping(target = "tipoVehiculo", ignore = true)
    Espacio toEntity(EspacioRequest request);
} 