package com.shujaa.parking_system.application.dtos.mapper;

import com.shujaa.parking_system.application.dtos.response.VehiculoResponse;
import com.shujaa.parking_system.domain.entity.Vehiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehiculoMapper {
    
    @Mapping(source = "tipoVehiculo.nombre", target = "tipoVehiculo")
    @Mapping(source = "tipoVehiculo.id", target = "idTipoVehiculo")
    @Mapping(source = "cliente.id", target = "idCliente")
    @Mapping(source = "cliente.dni", target = "dniCliente")
    @Mapping(source = "cliente.nombres", target = "nombresCliente")
    @Mapping(source = "cliente.apePaterno", target = "apePaternoCliente")
    @Mapping(source = "cliente.apeMaterno", target = "apeMaternoCliente")
    VehiculoResponse toResponse(Vehiculo vehiculo);
} 