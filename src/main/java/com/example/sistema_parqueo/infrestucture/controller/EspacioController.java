package com.example.sistema_parqueo.infrestucture.controller;

import com.example.sistema_parqueo.application.dtos.request.EspacioRequest;
import com.example.sistema_parqueo.application.dtos.response.ApiResponse;
import com.example.sistema_parqueo.application.dtos.response.EspacioResponse;
import com.example.sistema_parqueo.domain.port.input.IEspacioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/espacios")
@RequiredArgsConstructor
public class EspacioController {
    private final IEspacioService espacioService;

    @PostMapping
    public ResponseEntity<ApiResponse<EspacioResponse>> save(@Valid @RequestBody EspacioRequest request) {
        EspacioResponse espacio = espacioService.save(request);
        return ResponseEntity.ok(ApiResponse.success(espacio, "Espacio creado correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EspacioResponse>> update(@PathVariable Integer id, @Valid @RequestBody EspacioRequest request) {
        EspacioResponse espacio = espacioService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(espacio, "Espacio actualizado correctamente"));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<EspacioResponse>> toggleStatus(@PathVariable Integer id) {
        EspacioResponse espacio = espacioService.toggleStatus(id);
        return ResponseEntity.ok(ApiResponse.success(espacio, "Estado actualizado correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EspacioResponse>> findById(@PathVariable Integer id) {
        EspacioResponse espacio = espacioService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(espacio, "Espacio encontrado"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<EspacioResponse>>> findAll(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EspacioResponse> result = espacioService.findAll(query, estado, pageable);
        return ResponseEntity.ok(ApiResponse.success(result,"Espacios paginados"));
}
}
