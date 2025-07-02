package com.example.sistema_parqueo.application.dtos.mapper;

import com.example.sistema_parqueo.application.dtos.request.TipoVehiculoRequest;
import com.example.sistema_parqueo.application.dtos.response.TipoVehiculoResponse;
import com.example.sistema_parqueo.domain.entity.TipoVehiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoVehiculoMapper {
    TipoVehiculoResponse toResponse(TipoVehiculo tipoVehiculo);
    TipoVehiculo toEntity(TipoVehiculoRequest request);
} 