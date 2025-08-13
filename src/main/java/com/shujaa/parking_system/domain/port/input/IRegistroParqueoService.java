package com.shujaa.parking_system.domain.port.input;

import com.shujaa.parking_system.application.dtos.projections.RegistroParqueoProjection;
import com.shujaa.parking_system.application.dtos.request.RegistroEntradaRequest;
import com.shujaa.parking_system.application.dtos.request.RegistroSalidaRequest;
import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import com.shujaa.parking_system.application.dtos.response.RegistroParqueoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface IRegistroParqueoService {
    ApiResponse<RegistroParqueoProjection> registrarEntrada(RegistroEntradaRequest request);

    ApiResponse<RegistroParqueoProjection> registrarSalida(RegistroSalidaRequest request);

    ApiResponse<RegistroParqueoProjection> obtenerRegistroActivo(Integer idEspacio);

    Page<RegistroParqueoResponse> findAllList(String query, Integer idMetodoPago,
                                              LocalDateTime desde, LocalDateTime hasta,
                                              String estado, Pageable pageable);

    RegistroParqueoResponse findById(Integer id);
}