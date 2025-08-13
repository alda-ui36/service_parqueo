package com.shujaa.parking_system.application.service;

import com.shujaa.parking_system.application.dtos.mapper.VehiculoMapper;
import com.shujaa.parking_system.application.dtos.response.VehiculoResponse;
import com.shujaa.parking_system.domain.port.input.IVehiculoService;
import com.shujaa.parking_system.domain.port.output.IVehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements IVehiculoService {
    
    private final IVehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;
    
    @Override
    public VehiculoResponse buscarPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa)
                .map(vehiculoMapper::toResponse)
                .orElse(null);
    }

} 