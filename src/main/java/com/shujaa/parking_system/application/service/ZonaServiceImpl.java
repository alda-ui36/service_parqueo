package com.shujaa.parking_system.application.service;

import com.shujaa.parking_system.application.dtos.mapper.ZonaMapper;
import com.shujaa.parking_system.application.dtos.request.ZonaRequest;
import com.shujaa.parking_system.application.dtos.response.ZonaResponse;
import com.shujaa.parking_system.domain.entity.Zona;
import com.shujaa.parking_system.domain.port.input.IZonaService;
import com.shujaa.parking_system.domain.port.output.IZonaRepository;
import com.shujaa.parking_system.domain.port.output.IEspacioRepository;
import com.shujaa.parking_system.infrestucture.advices.ParkingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZonaServiceImpl implements IZonaService {
    private final IZonaRepository zonaRepository;
    private final IEspacioRepository espacioRepository;
    private final ZonaMapper zonaMapper;

    @Override
    public ZonaResponse save(ZonaRequest request) {
        Zona zona = zonaMapper.toEntity(request);
        zona.setEstado(true);
        return zonaMapper.toResponse(zonaRepository.save(zona));
    }

    @Override
    public ZonaResponse update(Integer id, ZonaRequest request) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new ParkingNotFoundException("Zona no encontrada"));
        long espaciosCreados = espacioRepository.countByZonaId(zona.getId());
        if (request.getTotalEspacios() < espaciosCreados) {
            throw new IllegalArgumentException("No se puede reducir el total de espacios por debajo de los espacios ya creados: " + espaciosCreados);
        }
        zona.setNombre(request.getNombre());
        zona.setDescripcion(request.getDescripcion());
        zona.setTotalEspacios(request.getTotalEspacios());
        return zonaMapper.toResponse(zonaRepository.save(zona));
    }

    @Override
    public ZonaResponse toggleStatus(Integer id) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new ParkingNotFoundException("Zona no encontrada"));
        zona.setEstado(!zona.getEstado());
        return zonaMapper.toResponse(zonaRepository.save(zona));
    }

    @Override
    public ZonaResponse findById(Integer id) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new ParkingNotFoundException("Zona no encontrada"));
        return zonaMapper.toResponse(zona);
    }

    @Override
    public Page<ZonaResponse> findAll(String query, Boolean estado, Pageable pageable) {
        return zonaRepository.findAll((root, cq, cb) -> {
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
        }, pageable).map(zonaMapper::toResponse);
    }
} 