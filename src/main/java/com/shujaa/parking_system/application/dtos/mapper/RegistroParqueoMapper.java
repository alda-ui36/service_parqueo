package com.shujaa.parking_system.application.dtos.mapper;

import com.shujaa.parking_system.application.dtos.response.RegistroParqueoResponse;
import com.shujaa.parking_system.domain.entity.RegistroParqueo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RegistroParqueoMapper {
    @Mappings({
        @Mapping(source = "cliente.id", target = "idCliente"),
        @Mapping(source = "cliente.nombres", target = "nombresCliente"),
        @Mapping(source = "cliente.apePaterno", target = "apePaternoCliente"),
        @Mapping(source = "cliente.apeMaterno", target = "apeMaternoCliente"),
        @Mapping(source = "cliente.dni", target = "dniCliente"),
        @Mapping(source = "cliente.telefono", target = "telefonoCliente"),
        @Mapping(source = "cliente.correo", target = "correoCliente"),
        @Mapping(source = "vehiculo.id", target = "idVehiculo"),
        @Mapping(source = "vehiculo.placa", target = "placaVehiculo"),
        @Mapping(source = "vehiculo.marca", target = "marcaVehiculo"),
        @Mapping(source = "vehiculo.color", target = "colorVehiculo"),
        @Mapping(source = "vehiculo.tipoVehiculo.nombre", target = "tipoVehiculo"),
        @Mapping(source = "vehiculo.tipoVehiculo.id", target = "idTipoVehiculo"),
        @Mapping(source = "espacio.id", target = "idEspacio"),
        @Mapping(source = "espacio.numeroEspacio", target = "numeroEspacio"),
        @Mapping(source = "espacio.zona.nombre", target = "zonaNombre"),
        @Mapping(source = "metodoPago.id", target = "idMetodoPago"),
        @Mapping(source = "metodoPago.nombre", target = "nombreMetodoPago"),
        @Mapping(source = "creadoPor.id", target = "idCreadoPor"),
        @Mapping(source = "creadoPor.username", target = "nombreCreadoPor"),
        @Mapping(source = "actualizadoPor.id", target = "idActualizadoPor"),
        @Mapping(source = "actualizadoPor.username", target = "nombreActualizadoPor")
    })
    RegistroParqueoResponse toResponse(RegistroParqueo entity);
} 