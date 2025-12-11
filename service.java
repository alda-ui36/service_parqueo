package com.example.demo.service.impl;

import com.example.demo.dto.request.ReservaRequest;
import com.example.demo.dto.response.ReservaResponse;
import com.example.demo.model.Habitacion;
import com.example.demo.model.Reserva;
import com.example.demo.repository.IHabitacionRepository;
import com.example.demo.repository.IReservaRepository;
import com.example.demo.service.IReservaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * service.
 */
@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements IReservaService {
  private final IHabitacionRepository habitacionRepository;
  private final IReservaRepository reservaRepository;

  @Override
  public ReservaResponse save(ReservaRequest request) {
    var entities = reservaRepository.save(toEntity(request));
    return toResponse(entities);
  }

  @Override
  public ReservaResponse update(ReservaRequest request, Long id) {
    Reserva entities = reservaRepository.findById(id).orElseThrow(null);
    entities.setName(request.getName());
    entities.setHabitacion(habiById(request.getIdHabitacion()));
    return toResponse(entities);
  }

  @Override
  public ReservaResponse byId(Long id) {
    return toResponse(reservaRepository.findById(id).orElseThrow(null));
  }


  @Override
  public List<ReservaResponse> all() {
    return reservaRepository.findAll().stream().map(this::toResponse).toList();
  }

  @Override
  public void delete(Long id) {
    reservaRepository.deleteById(id);
  }

  private Habitacion habiById(Long id) {
    return habitacionRepository.findById(id).orElseThrow(
            () -> new RuntimeException("not found id"));
  }

  private Reserva toEntity(ReservaRequest request) {
    Reserva entity = new Reserva();
    entity.setName(request.getName());
    entity.setHabitacion(habiById(request.getIdHabitacion()));
    return entity;
  }

  private ReservaResponse toResponse(Reserva entity) {
    ReservaResponse response = new ReservaResponse();
    response.setId(entity.getId());
    response.setName(entity.getName());
    response.setIdHabitacion(entity.getHabitacion().getId());
    return response;
  }
}
package com.example.demo.service.impl;

import com.example.demo.dto.request.HabitacionRequest;
import com.example.demo.dto.response.HabitacionResponse;
import com.example.demo.model.Habitacion;
import com.example.demo.repository.IHabitacionRepository;
import com.example.demo.service.IHabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service.
 */
@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements IHabitacionService {

  private final IHabitacionRepository repository;

  @Override
  public HabitacionResponse save(HabitacionRequest request) {
    Habitacion entity = toEntity(request);
    return toResponse(repository.save(entity));
  }

  @Override
  public HabitacionResponse update(HabitacionRequest request, Long id) {
    Habitacion entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    entity.setName(request.getName());
    return toResponse(repository.save(entity));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Override
  public List<HabitacionResponse> all() {
    return repository.findAll().stream()
            .map(this::toResponse)
            .toList();
  }

  @Override
  public HabitacionResponse byId(Long id) {
    Habitacion entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    return toResponse(entity);
  }

  @Override
  public HabitacionResponse byIdName(String name) {
    Habitacion entity = repository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    return toResponse(entity);
  }

  private Habitacion toEntity(HabitacionRequest request) {
    Habitacion entity = new Habitacion();
    entity.setName(request.getName());
    return entity;
  }

  private HabitacionResponse toResponse(Habitacion entity) {
    HabitacionResponse response = new HabitacionResponse();
    response.setId(entity.getId());
    response.setName(entity.getName());
    response.setStatus(entity.getStatus());
    return response;
  }
}
