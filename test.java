package com.example.demo.service.impl;

import com.example.demo.dto.request.HabitacionRequest;
import com.example.demo.model.Habitacion;
import com.example.demo.repository.IHabitacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitacionServiceImplTest {

  @Mock
  IHabitacionRepository repository;
  @InjectMocks
  HabitacionServiceImpl habitacionService;

  @Test
  void save() {
    var h2 = new HabitacionRequest();
    h2.setName("hola");
    var h1 = new Habitacion();
    h1.setId(1L);
    h1.setName("hola");

    when(repository.save(any())).thenReturn(h1);

    var response = habitacionService.save(h2);

    assertEquals("hola", response.getName());
    verify(repository).save(any());
  }

  @Test
  void update() {

    var request = new  HabitacionRequest();
    request.setName("nueva");
    var entity = new  Habitacion();
    entity.setId(1L);
    entity.setName("vieja");

    when(repository.findById(1L)).thenReturn(Optional.of(entity));
    when(repository.save(any())).thenReturn(entity);

    var response = habitacionService.update(request,1L);

    assertEquals("nueva",response.getName());
    verify(repository).save(any());
  }

  @Test
  void delete() {
    doNothing().when(repository).deleteById(1L);
    habitacionService.delete(1L);
    verify(repository).deleteById(1L);
  }

  @Test
  void all() {

    var h1 = new Habitacion();
    h1.setId(1L);
    h1.setName("hola");

    when(repository.findAll()).thenReturn(List.of(h1));

    var response = habitacionService.all();

    assertEquals(1, response.size());
    verify(repository).findAll();

  }

  @Test
  void byId() {

    var h1 = new Habitacion();
    h1.setId(1L);
    h1.setName("hola");

    when(repository.findById(1L)).thenReturn(Optional.of(h1));

    var response = habitacionService.byId(1L);

    assertEquals("hola", response.getName());
    verify(repository).findById(1L);
  }

  @Test
  void byIdName() {
    var h1 = new Habitacion();
    h1.setId(1L);
    h1.setName("hola");

    when(repository.findByName("hola")).thenReturn(Optional.of(h1));

    var response = habitacionService.byIdName("hola");

    assertEquals("hola", response.getName());
    verify(repository).findByName("hola");
  }
}
