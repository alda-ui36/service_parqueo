package com.example.sistema_parqueo.infrestucture.controller;

import com.example.sistema_parqueo.application.dtos.request.MetodoPagoRequest;
import com.example.sistema_parqueo.application.dtos.response.ApiResponse;
import com.example.sistema_parqueo.application.dtos.response.MetodoPagoResponse;
import com.example.sistema_parqueo.domain.port.input.IMetodoPagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metodos-pago")
@RequiredArgsConstructor
public class MetodoPagoController {
    private final IMetodoPagoService metodoPagoService;

    @PostMapping
    public ResponseEntity<ApiResponse<MetodoPagoResponse>> save(@Valid @RequestBody MetodoPagoRequest request) {
        MetodoPagoResponse response = metodoPagoService.save(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Método de pago creado correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MetodoPagoResponse>> update(@PathVariable Integer id, @Valid @RequestBody MetodoPagoRequest request) {
        MetodoPagoResponse response = metodoPagoService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Método de pago actualizado correctamente"));
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse<MetodoPagoResponse>> toggleStatus(@PathVariable Integer id) {
        MetodoPagoResponse response = metodoPagoService.toggleStatus(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Estado actualizado correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MetodoPagoResponse>> findById(@PathVariable Integer id) {
        MetodoPagoResponse response = metodoPagoService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Método de pago encontrado"));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MetodoPagoResponse>>> findAll(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<MetodoPagoResponse> result = metodoPagoService.findAll(query, estado, pageable);
        return ResponseEntity.ok(ApiResponse.success(result, "Métodos de pago paginados"));
    }
} 