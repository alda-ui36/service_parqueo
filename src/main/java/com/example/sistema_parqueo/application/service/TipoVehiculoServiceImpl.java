package com.example.sistema_parqueo.application.service;


import com.example.sistema_parqueo.application.dtos.mapper.TipoVehiculoMapper;
import com.example.sistema_parqueo.application.dtos.request.TipoVehiculoRequest;
import com.example.sistema_parqueo.application.dtos.response.TipoVehiculoResponse;
import com.example.sistema_parqueo.domain.entity.TipoVehiculo;
import com.example.sistema_parqueo.domain.port.input.ITipoVehiculoService;
import com.example.sistema_parqueo.domain.port.output.ITipoVehiculoRepository;
import com.example.sistema_parqueo.infrestucture.advices.ParqueoNotfoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipoVehiculoServiceImpl implements ITipoVehiculoService {
    private final ITipoVehiculoRepository tipoVehiculoRepository;
    private final TipoVehiculoMapper tipoVehiculoMapper;

    @Override
    public TipoVehiculoResponse save(TipoVehiculoRequest request) {
        TipoVehiculo tipoVehiculo = tipoVehiculoMapper.toEntity(request);
        tipoVehiculo.setEstado(true);
        return tipoVehiculoMapper.toResponse(tipoVehiculoRepository.save(tipoVehiculo));
    }

    @Override
    public TipoVehiculoResponse update(Integer id, TipoVehiculoRequest request) {
        TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(id)
                .orElseThrow(() -> new ParqueoNotfoundException("Tipo de vehículo no encontrado"));
        tipoVehiculo.setNombre(request.getNombre());
        tipoVehiculo.setTarifaHora(request.getTarifaHora());
        tipoVehiculo.setDescripcion(request.getDescripcion());
        return tipoVehiculoMapper.toResponse(tipoVehiculoRepository.save(tipoVehiculo));
    }

    @Override
    public TipoVehiculoResponse toggleStatus(Integer id) {
        TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(id)
                .orElseThrow(() -> new ParqueoNotfoundException("Tipo de vehículo no encontrado"));
        tipoVehiculo.setEstado(!tipoVehiculo.getEstado());
        return tipoVehiculoMapper.toResponse(tipoVehiculoRepository.save(tipoVehiculo));
    }

    @Override
    public TipoVehiculoResponse findById(Integer id) {
        TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(id)
                .orElseThrow(() -> new ParqueoNotfoundException("Tipo de vehículo no encontrado"));
        return tipoVehiculoMapper.toResponse(tipoVehiculo);
    }


    @Override
    public Page<TipoVehiculoResponse> findAll(String query, Boolean estado, Pageable pageable) {
        return tipoVehiculoRepository.findAll((root, cq, cb) -> {
            var predicates = cb.conjunction();
            if (query != null && !query.isBlank()) {
                String likeQuery = "%" + query.toLowerCase() + "%";
                predicates = cb.and(predicates, cb.or(
                        cb.like(cb.lower(root.get("nombre")), likeQuery),
                        cb.like(cb.lower(root.get("descripcion")), likeQuery)
                ));
            }
            if (estado != null) {
                predicates = cb.and(predicates, cb.equal(root.get("estado"), estado));
            }
            return predicates;
        }, pageable).map(tipoVehiculoMapper::toResponse);
    }
} 