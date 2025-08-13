package com.shujaa.parking_system.domain.port.input;

import com.shujaa.parking_system.application.dtos.request.EspacioRequest;
import com.shujaa.parking_system.application.dtos.response.EspacioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IEspacioService {
    EspacioResponse save(EspacioRequest request);
    EspacioResponse update(Integer id, EspacioRequest request);
    EspacioResponse toggleStatus(Integer id);
    EspacioResponse findById(Integer id);
    Page<EspacioResponse> findAll(String query, Boolean estado, Pageable pageable);
} 