package com.shujaa.parking_system.infrestucture.controller;

import com.shujaa.parking_system.application.dtos.request.EspacioRequest;
import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import com.shujaa.parking_system.application.dtos.response.EspacioResponse;
import com.shujaa.parking_system.domain.port.input.IEspacioService;
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
@RequestMapping("/api/espacios")
@RequiredArgsConstructor
public class EspacioController {
    private final IEspacioService espacioService;

    @PostMapping
    public ResponseEntity<ApiResponse<EspacioResponse>> save(@Valid @RequestBody EspacioRequest request) {
        EspacioResponse response = espacioService.save(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Espacio creado correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EspacioResponse>> update(@PathVariable Integer id, @Valid @RequestBody EspacioRequest request) {
        EspacioResponse response = espacioService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Espacio actualizado correctamente"));
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse<EspacioResponse>> toggleStatus(@PathVariable Integer id) {
        EspacioResponse response = espacioService.toggleStatus(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Estado actualizado correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EspacioResponse>> findById(@PathVariable Integer id) {
        EspacioResponse response = espacioService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Espacio encontrado"));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<EspacioResponse>>> findAll(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<EspacioResponse> result = espacioService.findAll(query, estado, pageable);
        return ResponseEntity.ok(ApiResponse.success(result, "Espacios paginados"));
    }
} 