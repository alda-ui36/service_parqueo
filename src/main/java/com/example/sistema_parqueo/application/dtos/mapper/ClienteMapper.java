package com.example.sistema_parqueo.application.dtos.mapper;

import com.example.sistema_parqueo.application.dtos.request.ClienteRequest;
import com.example.sistema_parqueo.application.dtos.response.ClienteResponse;
import com.example.sistema_parqueo.domain.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteResponse toResponse(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Cliente toEntity(ClienteRequest request);
}
