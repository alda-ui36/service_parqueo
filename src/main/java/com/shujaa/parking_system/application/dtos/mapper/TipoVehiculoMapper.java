package com.shujaa.parking_system.application.dtos.mapper;

import com.shujaa.parking_system.application.dtos.request.TipoVehiculoRequest;
import com.shujaa.parking_system.application.dtos.response.TipoVehiculoResponse;
import com.shujaa.parking_system.domain.entity.TipoVehiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoVehiculoMapper {
    TipoVehiculoResponse toResponse(TipoVehiculo tipoVehiculo);
    TipoVehiculo toEntity(TipoVehiculoRequest request);
} 