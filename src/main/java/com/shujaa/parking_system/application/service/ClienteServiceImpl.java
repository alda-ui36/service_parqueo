package com.shujaa.parking_system.application.service;

import com.shujaa.parking_system.application.dtos.mapper.ClienteMapper;
import com.shujaa.parking_system.application.dtos.request.ClienteRequest;
import com.shujaa.parking_system.application.dtos.response.ClienteResponse;
import com.shujaa.parking_system.application.dtos.specification.ClienteSpecification;
import com.shujaa.parking_system.application.utils.ClienteUtils;
import com.shujaa.parking_system.application.utils.ReniecUtils;
import com.shujaa.parking_system.domain.entity.Cliente;
import com.shujaa.parking_system.domain.port.input.IClienteService;
import com.shujaa.parking_system.domain.port.output.IClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {
    private final IClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final ReniecUtils reniecUtils;

    @Override
    @Transactional
    public ClienteResponse save(ClienteRequest request) {
        ClienteUtils.validateDniUnique(request.getDni(), null, clienteRepository);
        Cliente cliente = clienteMapper.toEntity(request);
        cliente.setEstado(true);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public ClienteResponse update(Integer id, ClienteRequest request) {
        Cliente cliente = ClienteUtils.getClienteById(id, clienteRepository);
        ClienteUtils.validateDniUnique(request.getDni(), cliente.getDni(), clienteRepository);
        updateClienteFields(cliente, request);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public ClienteResponse toggleStatus(Integer id) {
        Cliente cliente = ClienteUtils.getClienteById(id, clienteRepository);
        cliente.setEstado(!cliente.getEstado());
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public List<ClienteResponse> findAllActive() {
        return clienteRepository.findAll((root, cq, cb) -> cb.isTrue(root.get("estado")))
                .stream().map(clienteMapper::toResponse).toList();
    }

    @Override
    public Page<ClienteResponse> findAll(String query, Boolean estado, Pageable pageable) {
        return clienteRepository.findAll(ClienteSpecification.filterBy(query, estado), pageable)
                .map(clienteMapper::toResponse);
    }

    @Override
    public ClienteResponse buscarPorDni(String dni) {
        // Primero buscamos en nuestra base de datos
        Optional<Cliente> clienteLocal = clienteRepository.findByDni(dni);
        if (clienteLocal.isPresent()) {
            ClienteResponse response = clienteMapper.toResponse(clienteLocal.get());
            response.setOrigen("LOCAL");
            return response;
        }

        // Si no existe en BD, consultamos RENIEC
        Cliente clienteReniec = reniecUtils.buscarPersonaPorDni(dni);
        if (clienteReniec != null) {
            ClienteResponse response = clienteMapper.toResponse(clienteReniec);
            response.setOrigen("RENIEC");
            return response;
        }

        return null;
    }

    // Método específico de cliente para actualizar campos
    private void updateClienteFields(Cliente cliente, ClienteRequest request) {
        cliente.setNombres(request.getNombres());
        cliente.setApePaterno(request.getApePaterno());
        cliente.setApeMaterno(request.getApeMaterno());
        cliente.setDni(request.getDni());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());
        cliente.setCorreo(request.getCorreo());
    }
}