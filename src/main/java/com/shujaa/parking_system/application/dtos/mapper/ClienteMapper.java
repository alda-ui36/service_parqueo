package com.shujaa.parking_system.application.dtos.mapper;

import com.shujaa.parking_system.application.dtos.request.ClienteRequest;
import com.shujaa.parking_system.application.dtos.response.ClienteResponse;
import com.shujaa.parking_system.domain.entity.Cliente;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteResponse toResponse(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Cliente toEntity(ClienteRequest request);
} 