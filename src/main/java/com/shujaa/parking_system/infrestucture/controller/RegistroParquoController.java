package com.shujaa.parking_system.infrestucture.controller;

import com.shujaa.parking_system.application.dtos.projections.RegistroParqueoProjection;
import com.shujaa.parking_system.application.dtos.request.RegistroEntradaRequest;
import com.shujaa.parking_system.application.dtos.request.RegistroSalidaRequest;
import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import com.shujaa.parking_system.domain.port.input.IRegistroParqueoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parqueo")
@RequiredArgsConstructor
public class RegistroParquoController {

    private final IRegistroParqueoService registroParqueoService;

    @PostMapping("/entrada")
    public ResponseEntity<ApiResponse<RegistroParqueoProjection>> registrarEntrada(@RequestBody RegistroEntradaRequest request){
        ApiResponse<RegistroParqueoProjection> response = registroParqueoService.registrarEntrada(request);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/salida")
    public ResponseEntity<ApiResponse<RegistroParqueoProjection>> registrarSalida(@RequestBody RegistroSalidaRequest request){
        ApiResponse<RegistroParqueoProjection> response = registroParqueoService.registrarSalida(request);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/espacio/{id}")
    public ResponseEntity<ApiResponse<RegistroParqueoProjection>> obtenerRegistroPorEspacio(@PathVariable("id") Integer idEspacio) {
        ApiResponse<RegistroParqueoProjection> response = registroParqueoService.obtenerRegistroActivo(idEspacio);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
