package com.example.sistema_parqueo.application.service;

import com.example.sistema_parqueo.application.dtos.mapper.EspacioMapper;
import com.example.sistema_parqueo.application.dtos.request.EspacioRequest;
import com.example.sistema_parqueo.application.dtos.response.EspacioResponse;
import com.example.sistema_parqueo.application.utils.EspacioUtils;
import com.example.sistema_parqueo.domain.entity.Espacio;
import com.example.sistema_parqueo.domain.entity.TipoVehiculo;
import com.example.sistema_parqueo.domain.entity.Zona;
import com.example.sistema_parqueo.domain.port.input.IEspacioService;
import com.example.sistema_parqueo.domain.port.output.IEspacioRepository;
import com.example.sistema_parqueo.domain.port.output.ITipoVehiculoRepository;
import com.example.sistema_parqueo.domain.port.output.IZonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EspacioServiceIpml implements IEspacioService {

    private final IEspacioRepository espacioRepository;
    private final IZonaRepository zonaRepository;
    private final EspacioMapper espacioMapper;
    private final ITipoVehiculoRepository tipoVehiculoRepository;

    @Override
    public EspacioResponse save(EspacioRequest request) {
        Zona zona = EspacioUtils.getZonaById(request.getZonaId(),zonaRepository);
        TipoVehiculo tipoVehiculo = EspacioUtils.gettipoVehiculoById(request.getTipoVehiculoId(),tipoVehiculoRepository);
        EspacioUtils.validarCupoDisponible(zona, espacioRepository);
        EspacioUtils.validarEspacioUnico(zona.getId(),request.getNumeroEspacio(),null,espacioRepository);
        Espacio espacio = espacioMapper.toEntity(request);
        espacio.setZona(zona);
        espacio.setTipoVehiculo(tipoVehiculo);
        espacio.setEstado(true);
        espacio.setOcupado(false);
        return espacioMapper.toResponse(espacioRepository.save(espacio));
    }

    @Override
    public EspacioResponse update(Integer id, EspacioRequest request) {
        Espacio espacio = EspacioUtils.getEspacioById(id,espacioRepository);
        TipoVehiculo tipoVehiculo = EspacioUtils.gettipoVehiculoById(request.getTipoVehiculoId(),tipoVehiculoRepository);
        Zona zona = EspacioUtils.getZonaById(request.getZonaId(),zonaRepository);
        EspacioUtils.validarEspacioUnico(zona.getId(),request.getNumeroEspacio(),espacio.getNumeroEspacio(),espacioRepository);

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
        return espacioRepository.findAll((root, cq, cb) -> {;
            var predicates = cb.conjunction();
            if (query != null && !query.isBlank()) {
                String likeQuery = "%" + query.toLowerCase() + "%";
                predicates = cb.and(predicates, cb.or(
                        cb.like(cb.lower(root.get("numeroEspacio")), likeQuery),
                        cb.like(cb.lower(root.get("zona").get("nombre")), likeQuery),
                        cb.like(cb.lower(root.get("tipoVehiculo").get("nombre")), likeQuery)
                ));
            }
            if (estado != null) {
                predicates = cb.and(predicates, cb.equal(root.get("estado"), estado));
            }
            return predicates;
        }, pageable).map(espacioMapper::toResponse);
    }
}

