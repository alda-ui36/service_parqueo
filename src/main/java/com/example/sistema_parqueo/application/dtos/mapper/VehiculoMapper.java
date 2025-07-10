package com.example.sistema_parqueo.application.dtos.mapper;

import com.example.sistema_parqueo.application.dtos.response.VehiculoResponse;
import com.example.sistema_parqueo.domain.entity.Vehiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehiculoMapper {
    @Mapping(source = "tipoVehiculo.nombre", target = "tipoVehiculo")
    @Mapping(source = "tipoVehiculo.id", target = "idTipoVehiculo")
    @Mapping(source = "cliente.id",target = "idCliente" )
    @Mapping(source = "cliente.dni",target = "dniCliente" )
    @Mapping(source = "cliente.nombres",target = "nombresCliente" )
    @Mapping(source = "cliente.apePaterno",target = "apeParternoCliente" )
    @Mapping(source = "cliente.apeMaterno",target = "apeMaternoCliente" )
    VehiculoResponse toResponse(Vehiculo vehiculo);
}
