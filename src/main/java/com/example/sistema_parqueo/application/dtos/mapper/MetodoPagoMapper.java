package com.example.sistema_parqueo.application.dtos.mapper;

import com.example.sistema_parqueo.application.dtos.request.MetodoPagoRequest;
import com.example.sistema_parqueo.application.dtos.response.MetodoPagoResponse;
import com.example.sistema_parqueo.domain.entity.MetodoPago;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MetodoPagoMapper {
    MetodoPagoResponse toResponse(MetodoPago metodoPago);
    MetodoPago toEntity(MetodoPagoRequest request);
} 