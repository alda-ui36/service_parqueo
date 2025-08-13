package com.shujaa.parking_system.application.service;

import com.shujaa.parking_system.application.dtos.mapper.MetodoPagoMapper;
import com.shujaa.parking_system.application.dtos.request.MetodoPagoRequest;
import com.shujaa.parking_system.application.dtos.response.MetodoPagoResponse;
import com.shujaa.parking_system.domain.entity.MetodoPago;
import com.shujaa.parking_system.domain.port.input.IMetodoPagoService;
import com.shujaa.parking_system.domain.port.output.IMetodoPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetodoPagoServiceImpl implements IMetodoPagoService {
    private final IMetodoPagoRepository metodoPagoRepository;
    private final MetodoPagoMapper metodoPagoMapper;

    @Transactional
    @Override
    public MetodoPagoResponse save(MetodoPagoRequest request) {
        MetodoPago metodoPago = metodoPagoMapper.toEntity(request);
        metodoPago.setEstado(true);
        return metodoPagoMapper.toResponse(metodoPagoRepository.save(metodoPago));
    }

    @Transactional
    @Override
    public MetodoPagoResponse update(Integer id, MetodoPagoRequest request) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Método de pago no encontrado"));
        metodoPago.setNombre(request.getNombre());
        return metodoPagoMapper.toResponse(metodoPagoRepository.save(metodoPago));
    }

    @Transactional
    @Override
    public MetodoPagoResponse toggleStatus(Integer id) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Método de pago no encontrado"));
        metodoPago.setEstado(!metodoPago.getEstado());
        return metodoPagoMapper.toResponse(metodoPagoRepository.save(metodoPago));
    }

    @Override
    public MetodoPagoResponse findById(Integer id) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Método de pago no encontrado"));
        return metodoPagoMapper.toResponse(metodoPago);
    }


    @Override
    public Page<MetodoPagoResponse> findAll(String query, Boolean estado, Pageable pageable) {
        var all = metodoPagoRepository.findAll().stream()
                .filter(m -> (query == null || m.getNombre().toLowerCase().contains(query.toLowerCase())))
                .filter(m -> (estado == null || Boolean.valueOf(estado).equals(m.getEstado())))
                .map(metodoPagoMapper::toResponse)
                .toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), all.size());
        List<MetodoPagoResponse> pageContent = (start > end) ? List.of() : all.subList(start, end);
        return new PageImpl<>(pageContent, pageable, all.size());
    }
} 