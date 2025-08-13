package com.shujaa.parking_system.domain.port.input;

import com.shujaa.parking_system.application.dtos.response.VehiculoResponse;

public interface IVehiculoService {
    VehiculoResponse buscarPorPlaca(String placa);
}