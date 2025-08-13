package com.shujaa.parking_system.application.service;

import com.shujaa.parking_system.application.dtos.mapper.EspacioMapper;
import com.shujaa.parking_system.application.dtos.request.EspacioRequest;
import com.shujaa.parking_system.application.dtos.response.EspacioResponse;
import com.shujaa.parking_system.application.utils.EspacioUtils;
import com.shujaa.parking_system.domain.entity.Espacio;
import com.shujaa.parking_system.domain.entity.TipoVehiculo;
import com.shujaa.parking_system.domain.entity.Zona;
import com.shujaa.parking_system.domain.port.input.IEspacioService;
import com.shujaa.parking_system.domain.port.output.IEspacioRepository;
import com.shujaa.parking_system.domain.port.output.ITipoVehiculoRepository;
import com.shujaa.parking_system.domain.port.output.IZonaRepository;
import com.shujaa.parking_system.infrestucture.advices.ParkingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EspacioServiceImpl implements IEspacioService {
    private final IEspacioRepository espacioRepository;
    private final IZonaRepository zonaRepository;
    private final ITipoVehiculoRepository tipoVehiculoRepository;
    private final EspacioMapper espacioMapper;

    @Override
    public EspacioResponse save(EspacioRequest request) {
        Zona zona = EspacioUtils.getZonaById(request.getZonaId(), zonaRepository);
        EspacioUtils.validateCupoDisponible(zona, espacioRepository);
        EspacioUtils.validateNumeroEspacioUnico(zona.getId(), request.getNumeroEspacio(), null, espacioRepository);
        TipoVehiculo tipoVehiculo = EspacioUtils.getTipoVehiculoById(request.getTipoVehiculoId(), tipoVehiculoRepository);
        Espacio espacio = espacioMapper.toEntity(request);
        espacio.setZona(zona);
        espacio.setTipoVehiculo(tipoVehiculo);
        espacio.setEstado(true);
        espacio.setOcupado(false); // Siempre se crea como libre
        return espacioMapper.toResponse(espacioRepository.save(espacio));
    }

    @Override
    public EspacioResponse update(Integer id, EspacioRequest request) {
        Espacio espacio = EspacioUtils.getEspacioById(id, espacioRepository);
        Zona zona = EspacioUtils.getZonaById(request.getZonaId(), zonaRepository);
        if (!espacio.getZona().getId().equals(zona.getId())) {
            EspacioUtils.validateCupoDisponible(zona, espacioRepository);
        }
        EspacioUtils.validateNumeroEspacioUnico(zona.getId(), request.getNumeroEspacio(), espacio.getNumeroEspacio(), espacioRepository);
        TipoVehiculo tipoVehiculo = EspacioUtils.getTipoVehiculoById(request.getTipoVehiculoId(), tipoVehiculoRepository);
        espacio.setZona(zona);
        espacio.setNumeroEspacio(request.getNumeroEspacio());
        espacio.setTipoVehiculo(tipoVehiculo);
        return espacioMapper.toResponse(espacioRepository.save(espacio));
    }

    @Override
    public EspacioResponse toggleStatus(Integer id) {
        Espacio espacio = EspacioUtils.getEspacioById(id, espacioRepository);
        espacio.setEstado(!espacio.getEstado());
        return espacioMapper.toResponse(espacioRepository.save(espacio));
    }

    @Override
    public EspacioResponse findById(Integer id) {
        return espacioMapper.toResponse(EspacioUtils.getEspacioById(id, espacioRepository));
    }

    @Override
    public Page<EspacioResponse> findAll(String query, Boolean estado, Pageable pageable) {
        return espacioRepository.findAll((root, cq, cb) -> {
            var predicates = cb.conjunction();
            if (query != null && !query.isBlank()) {
                String likeQuery = "%" + query.toLowerCase() + "%";
                predicates = cb.and(predicates, cb.or(
                        cb.like(cb.lower(root.get("numeroEspacio")), likeQuery)
                ));
            }
            if (estado != null) {
                predicates = cb.and(predicates, cb.equal(root.get("estado"), estado));
            }
            return predicates;
        }, pageable).map(espacioMapper::toResponse);
    }
} 