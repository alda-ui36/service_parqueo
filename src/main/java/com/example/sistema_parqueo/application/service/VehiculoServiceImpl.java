package com.example.sistema_parqueo.application.service;

import com.example.sistema_parqueo.application.dtos.mapper.VehiculoMapper;
import com.example.sistema_parqueo.application.dtos.response.VehiculoResponse;
import com.example.sistema_parqueo.domain.port.input.IVehiculoService;
import com.example.sistema_parqueo.domain.port.output.IVehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl  implements IVehiculoService {

    private final IVehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;

    @Override
    public VehiculoResponse buscarPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa)
                .map(vehiculoMapper::toResponse)
                .orElse(null);
    }
}
