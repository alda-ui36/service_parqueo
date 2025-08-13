package com.shujaa.parking_system.domain.port.input;

import com.shujaa.parking_system.application.dtos.request.ClienteRequest;
import com.shujaa.parking_system.application.dtos.response.ClienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IClienteService {
    ClienteResponse save(ClienteRequest request);

    ClienteResponse update(Integer id, ClienteRequest request);

    ClienteResponse toggleStatus(Integer id);

    List<ClienteResponse> findAllActive();

    Page<ClienteResponse> findAll(String query, Boolean estado, Pageable pageable);

    ClienteResponse buscarPorDni(String dni);
}