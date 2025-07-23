package com.shujaa.parking_system.domain.port.input;

import com.shujaa.parking_system.application.dtos.projections.RegistroParqueoProjection;
import com.shujaa.parking_system.application.dtos.request.RegistroEntradaRequest;
import com.shujaa.parking_system.application.dtos.request.RegistroSalidaRequest;
import com.shujaa.parking_system.application.dtos.response.ApiResponse;

public interface IRegistroParqueoService {

    ApiResponse<RegistroParqueoProjection> registrarEntrada(RegistroEntradaRequest request);
    ApiResponse<RegistroParqueoProjection> registrarSalida(RegistroSalidaRequest request);
    ApiResponse<RegistroParqueoProjection> obtenerRegistroActivo(Integer idEspacio);
}
