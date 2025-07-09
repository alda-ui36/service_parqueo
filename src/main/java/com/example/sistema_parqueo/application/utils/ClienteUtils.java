package com.example.sistema_parqueo.application.utils;

import com.example.sistema_parqueo.domain.entity.Cliente;
import com.example.sistema_parqueo.domain.port.output.IClienteRepository;

public class ClienteUtils {

    public static void validateDniUnique(String dni, String currentDni, IClienteRepository clienteRepository){
        CommonUtils.validateUniqueField(dni, currentDni, clienteRepository, "dni", "DNI");
    }

    public static Cliente getClienteById(Integer id, IClienteRepository clienteRepository) {
        return CommonUtils.findByIdOrThrow(id, clienteRepository, "Cliente");
    }

}
