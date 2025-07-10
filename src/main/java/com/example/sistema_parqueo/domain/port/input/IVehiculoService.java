package com.example.sistema_parqueo.domain.port.input;

import com.example.sistema_parqueo.application.dtos.response.VehiculoResponse;

public interface IVehiculoService {
    VehiculoResponse buscarPorPlaca(String placa);
}
