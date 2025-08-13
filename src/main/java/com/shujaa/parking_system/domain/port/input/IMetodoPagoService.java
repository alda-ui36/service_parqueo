package com.shujaa.parking_system.domain.port.input;

import com.shujaa.parking_system.application.dtos.request.MetodoPagoRequest;
import com.shujaa.parking_system.application.dtos.response.MetodoPagoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMetodoPagoService {
    MetodoPagoResponse save(MetodoPagoRequest request);
    MetodoPagoResponse update(Integer id, MetodoPagoRequest request);
    MetodoPagoResponse toggleStatus(Integer id);
    MetodoPagoResponse findById(Integer id);
    Page<MetodoPagoResponse> findAll(String query, Boolean estado, Pageable pageable);
} 