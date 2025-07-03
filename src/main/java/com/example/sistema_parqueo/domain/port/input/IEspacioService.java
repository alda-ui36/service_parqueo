package com.example.sistema_parqueo.domain.port.input;

import com.example.sistema_parqueo.application.dtos.request.EspacioRequest;
import com.example.sistema_parqueo.application.dtos.response.EspacioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEspacioService {
    EspacioResponse save(EspacioRequest request);
    EspacioResponse update(Integer id, EspacioRequest request);
    EspacioResponse toggleStatus(Integer id);
    EspacioResponse findById(Integer id);
    Page<EspacioResponse> findAll(String query, Boolean estado, Pageable pageable);
}
