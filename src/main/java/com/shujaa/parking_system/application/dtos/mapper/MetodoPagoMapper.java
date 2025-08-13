package com.shujaa.parking_system.application.dtos.mapper;

import com.shujaa.parking_system.application.dtos.response.MetodoPagoResponse;
import com.shujaa.parking_system.domain.entity.MetodoPago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import com.shujaa.parking_system.application.dtos.request.MetodoPagoRequest;

@Mapper(componentModel = "spring")
public interface MetodoPagoMapper {
    MetodoPagoResponse toResponse(MetodoPago metodoPago);
    MetodoPago toEntity(MetodoPagoRequest request);
} 