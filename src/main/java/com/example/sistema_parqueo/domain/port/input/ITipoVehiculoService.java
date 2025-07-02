package com.example.sistema_parqueo.domain.port.input;

import com.example.sistema_parqueo.application.dtos.request.TipoVehiculoRequest;
import com.example.sistema_parqueo.application.dtos.response.TipoVehiculoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITipoVehiculoService {
    TipoVehiculoResponse save(TipoVehiculoRequest request);
    TipoVehiculoResponse update(Integer id, TipoVehiculoRequest request);
    TipoVehiculoResponse toggleStatus(Integer id);
    TipoVehiculoResponse findById(Integer id);
    Page<TipoVehiculoResponse> findAll(String query, Boolean estado, Pageable pageable);
} 