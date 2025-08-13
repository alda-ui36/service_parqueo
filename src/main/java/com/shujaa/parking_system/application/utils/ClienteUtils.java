package com.shujaa.parking_system.application.utils;

import com.shujaa.parking_system.domain.entity.Cliente;
import com.shujaa.parking_system.domain.port.output.IClienteRepository;

public class ClienteUtils {
    
    public static void validateDniUnique(String dni, String currentDni, IClienteRepository clienteRepository) {
        CommonUtils.validateUniqueField(dni, currentDni, clienteRepository, "dni", "DNI");
    }

    public static Cliente getClienteById(Integer id, IClienteRepository clienteRepository) {
        return CommonUtils.findByIdOrThrow(id, clienteRepository, "Cliente");
    }
}