package com.shujaa.parking_system.domain.port.input;

import com.shujaa.parking_system.application.dtos.request.ZonaRequest;
import com.shujaa.parking_system.application.dtos.response.ZonaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IZonaService {
    ZonaResponse save(ZonaRequest request);
    ZonaResponse update(Integer id, ZonaRequest request);
    ZonaResponse toggleStatus(Integer id);
    ZonaResponse findById(Integer id);
    Page<ZonaResponse> findAll(String query, Boolean estado, Pageable pageable);
} 