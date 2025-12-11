package com.example.demo.controller;

import com.example.demo.dto.request.HabitacionRequest;
import com.example.demo.dto.response.HabitacionResponse;
import com.example.demo.service.IHabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

  private final IHabitacionService service;


  @PostMapping
  public ResponseEntity<HabitacionResponse> save(@RequestBody HabitacionRequest request) {
    try {
      return ResponseEntity.ok(service.save(request));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<HabitacionResponse> update(@PathVariable Long id, @RequestBody HabitacionRequest request) {
    try {
      return ResponseEntity.ok(service.update(request, id));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<HabitacionResponse>> all() {
    try {
      return ResponseEntity.ok(service.all());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<HabitacionResponse> byId(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(service.byId(id));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<HabitacionResponse> byName(@PathVariable String name) {
    try {
      return ResponseEntity.ok(service.byIdName(name));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    try {
      service.delete(id);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
