package com.shujaa.parking_system.domain.port.input;

import com.shujaa.parking_system.application.dtos.request.TipoVehiculoRequest;
import com.shujaa.parking_system.application.dtos.response.TipoVehiculoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ITipoVehiculoService {
    TipoVehiculoResponse save(TipoVehiculoRequest request);
    TipoVehiculoResponse update(Integer id, TipoVehiculoRequest request);
    TipoVehiculoResponse toggleStatus(Integer id);
    TipoVehiculoResponse findById(Integer id);
    Page<TipoVehiculoResponse> findAll(String query, Boolean estado, Pageable pageable);
} 