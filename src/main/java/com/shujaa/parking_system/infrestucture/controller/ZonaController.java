package com.shujaa.parking_system.infrestucture.controller;

import com.shujaa.parking_system.application.dtos.request.ZonaRequest;
import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import com.shujaa.parking_system.application.dtos.response.ZonaResponse;
import com.shujaa.parking_system.domain.port.input.IZonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/zonas")
@RequiredArgsConstructor
public class ZonaController {
    private final IZonaService zonaService;

    @PostMapping
    public ResponseEntity<ApiResponse<ZonaResponse>> save(@Valid @RequestBody ZonaRequest request) {
        ZonaResponse response = zonaService.save(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Zona creada correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ZonaResponse>> update(@PathVariable Integer id, @Valid @RequestBody ZonaRequest request) {
        ZonaResponse response = zonaService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Zona actualizada correctamente"));
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse<ZonaResponse>> toggleStatus(@PathVariable Integer id) {
        ZonaResponse response = zonaService.toggleStatus(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Estado actualizado correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ZonaResponse>> findById(@PathVariable Integer id) {
        ZonaResponse response = zonaService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Zona encontrada"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ZonaResponse>>> findAll(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ZonaResponse> result = zonaService.findAll(query, estado, pageable);
        return ResponseEntity.ok(ApiResponse.success(result, "Zonas paginadas"));
    }
} 