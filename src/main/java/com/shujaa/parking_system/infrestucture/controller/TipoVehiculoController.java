package com.shujaa.parking_system.infrestucture.controller;

import com.shujaa.parking_system.application.dtos.request.TipoVehiculoRequest;
import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import com.shujaa.parking_system.application.dtos.response.TipoVehiculoResponse;
import com.shujaa.parking_system.domain.port.input.ITipoVehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipo-vehiculos")
@RequiredArgsConstructor
public class TipoVehiculoController {
    private final ITipoVehiculoService tipoVehiculoService;

    @PostMapping
    public ResponseEntity<ApiResponse<TipoVehiculoResponse>> save(@Valid @RequestBody TipoVehiculoRequest request) {
        TipoVehiculoResponse response = tipoVehiculoService.save(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Tipo de vehículo creado correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TipoVehiculoResponse>> update(@PathVariable Integer id, @Valid @RequestBody TipoVehiculoRequest request) {
        TipoVehiculoResponse response = tipoVehiculoService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Tipo de vehículo actualizado correctamente"));
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse<TipoVehiculoResponse>> toggleStatus(@PathVariable Integer id) {
        TipoVehiculoResponse response = tipoVehiculoService.toggleStatus(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Estado actualizado correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TipoVehiculoResponse>> findById(@PathVariable Integer id) {
        TipoVehiculoResponse response = tipoVehiculoService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Tipo de vehículo encontrado"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TipoVehiculoResponse>>> findAll(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<TipoVehiculoResponse> result = tipoVehiculoService.findAll(query, estado, pageable);
        return ResponseEntity.ok(ApiResponse.success(result, "Tipos de vehículo paginados"));
    }
} 