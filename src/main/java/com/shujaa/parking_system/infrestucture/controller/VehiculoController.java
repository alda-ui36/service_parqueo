package com.shujaa.parking_system.infrestucture.controller;

import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import com.shujaa.parking_system.application.dtos.response.VehiculoResponse;
import com.shujaa.parking_system.domain.port.input.IVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {
    
    private final IVehiculoService vehiculoService;
    
    @GetMapping("/buscar/{placa}")
    public ResponseEntity<ApiResponse<VehiculoResponse>> buscarPorPlaca(@PathVariable String placa) {
        VehiculoResponse vehiculo = vehiculoService.buscarPorPlaca(placa);
        
        if (vehiculo != null) {
            return ResponseEntity.ok(ApiResponse.success(vehiculo, "Vehículo encontrado"));
        } else {
            return ResponseEntity.ok(ApiResponse.error("Vehículo no encontrado", 404));
        }
    }
} 