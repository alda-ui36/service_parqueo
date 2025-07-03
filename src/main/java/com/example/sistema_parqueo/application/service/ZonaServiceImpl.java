package com.example.sistema_parqueo.application.service;

import com.example.sistema_parqueo.application.dtos.mapper.ZonaMapper;
import com.example.sistema_parqueo.application.dtos.request.ZonaRequest;
import com.example.sistema_parqueo.application.dtos.response.ZonaResponse;
import com.example.sistema_parqueo.domain.entity.Zona;
import com.example.sistema_parqueo.domain.port.input.IZonaService;
import com.example.sistema_parqueo.domain.port.output.IEspacioRepository;
import com.example.sistema_parqueo.domain.port.output.IZonaRepository;
import com.example.sistema_parqueo.infrestucture.advices.ParqueoNotfoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZonaServiceImpl  implements IZonaService {

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
                .orElseThrow(() -> new ParqueoNotfoundException("Zona no encontrada"));
        long espaciosCreados = espacioRepository.countByZonaId(zona.getId());
        if(request.getTotalEspacios() < espaciosCreados){
            throw new  ParqueoNotfoundException("No se puede actualizar la zona, ya que tiene más espacios de los permitidos");
        }
        zona.setNombre(request.getNombre());
        zona.setDescripcion(request.getDescripcion());
        zona.setTotalEspacios(request.getTotalEspacios());
        return zonaMapper.toResponse(zonaRepository.save(zona));
    }

    @Override
    public ZonaResponse toggleStatus(Integer id) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new ParqueoNotfoundException("Zona no encontrada"));
        zona.setEstado(!zona.getEstado());
        return zonaMapper.toResponse(zonaRepository.save(zona));
    }

    @Override
    public ZonaResponse findById(Integer id) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new ParqueoNotfoundException("Zona no encontrada"));
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
