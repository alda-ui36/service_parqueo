package com.example.sistema_parqueo.infrestucture.controller;

import com.example.sistema_parqueo.application.dtos.response.ApiResponse;
import com.example.sistema_parqueo.application.dtos.response.VehiculoResponse;
import com.example.sistema_parqueo.domain.port.input.IVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {
    private final IVehiculoService vehiculoService;

    @GetMapping("/buscar/{placa}")
    public ResponseEntity<ApiResponse<VehiculoResponse>> buscarPorPlaca(@PathVariable String placa){
        VehiculoResponse vehiculo = vehiculoService.buscarPorPlaca(placa);
        if(vehiculo != null){
            return ResponseEntity.ok(ApiResponse.success(vehiculo, "Vehiculo encontrado"));
        }else{
            return  ResponseEntity.ok(ApiResponse.error("vehiculo no encotrado",404));
        }
    }
}
