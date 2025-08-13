package com.shujaa.parking_system.infrestucture.controller;

import com.shujaa.parking_system.application.dtos.projections.RegistroParqueoProjection;
import com.shujaa.parking_system.application.dtos.request.RegistroEntradaRequest;
import com.shujaa.parking_system.application.dtos.request.RegistroSalidaRequest;
import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import com.shujaa.parking_system.application.dtos.response.RegistroParqueoResponse;
import com.shujaa.parking_system.domain.port.input.IRegistroParqueoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/parqueo")
@RequiredArgsConstructor
public class RegistroParqueoController {

    private final IRegistroParqueoService registroParqueoService;

    @PostMapping("/entrada")
    public ResponseEntity<ApiResponse<RegistroParqueoProjection>> registrarEntrada(
            @RequestBody RegistroEntradaRequest request) {
        ApiResponse<RegistroParqueoProjection> response = registroParqueoService.registrarEntrada(request);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/salida")
    public ResponseEntity<ApiResponse<RegistroParqueoProjection>> registrarSalida(
            @RequestBody RegistroSalidaRequest request) {
        ApiResponse<RegistroParqueoProjection> response = registroParqueoService.registrarSalida(request);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/espacio/{id}")
    public ResponseEntity<ApiResponse<RegistroParqueoProjection>> obtenerRegistroActivo(
            @PathVariable("id") Integer idEspacio) {
        ApiResponse<RegistroParqueoProjection> response = registroParqueoService.obtenerRegistroActivo(idEspacio);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<RegistroParqueoResponse>>> findAllList(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer idMetodoPago,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "id")
        );
        Page<RegistroParqueoResponse> response = registroParqueoService.findAllList(query, idMetodoPago, desde, hasta, estado, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Lista de registros de parqueo obtenida"));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RegistroParqueoResponse>> findById(@PathVariable Integer id) {
        RegistroParqueoResponse response = registroParqueoService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Registro de parqueo encontrado"));
    }
}
