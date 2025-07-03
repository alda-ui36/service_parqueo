package com.example.sistema_parqueo.domain.port.input;

import com.example.sistema_parqueo.application.dtos.request.ZonaRequest;
import com.example.sistema_parqueo.application.dtos.response.ZonaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IZonaService {
    ZonaResponse save(ZonaRequest request);
    ZonaResponse update(Integer id, ZonaRequest request);
    ZonaResponse toggleStatus(Integer id);
    ZonaResponse findById(Integer id);
    Page<ZonaResponse> findAll(String query, Boolean estado, Pageable pageable);
}
