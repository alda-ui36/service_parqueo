package com.example.sistema_parqueo.application.dtos.mapper;

import com.example.sistema_parqueo.application.dtos.request.EspacioRequest;
import com.example.sistema_parqueo.application.dtos.response.EspacioResponse;
import com.example.sistema_parqueo.domain.entity.Espacio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
