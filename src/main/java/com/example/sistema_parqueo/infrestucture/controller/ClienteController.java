package com.example.sistema_parqueo.infrestucture.controller;

import com.example.sistema_parqueo.application.dtos.request.ClienteRequest;
import com.example.sistema_parqueo.application.dtos.response.ApiResponse;
import com.example.sistema_parqueo.application.dtos.response.ClienteResponse;
import com.example.sistema_parqueo.domain.port.input.IClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final IClienteService clienteService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponse>> save(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.save(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Cliente creado correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> update(@PathVariable Integer id, @Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Cliente actualizado correctamente"));
    }
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse<ClienteResponse>> toggleStatus(@PathVariable Integer id) {
        ClienteResponse response = clienteService.toggleStatus(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Estado actualizado correctamente"));
    }

    @GetMapping("/activos")
    public ResponseEntity<ApiResponse<List<ClienteResponse>>>  findAllActivos() {
        List<ClienteResponse> clientes = clienteService.findAllActive();
        return ResponseEntity.ok(ApiResponse.success(clientes, "Clientes activos encontrados"));
    }

    @GetMapping("/buscar/{dni}")
    public ResponseEntity<ApiResponse<ClienteResponse>> buscarPorDni(@PathVariable String dni){
        ClienteResponse cliente = clienteService.buscarPorDni(dni);
        if(cliente != null){
            String mensaje = cliente.getOrigen().equals("LOCAL")
                    ? "Cliente encontrado en la base de datos local"
                    : "Cliente encontrado en la Reniec";
            return ResponseEntity.ok(ApiResponse.success(cliente,mensaje));
        }else {
            return ResponseEntity.ok(ApiResponse.error("Cliente no encontrado",404));
        }

    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ClienteResponse>>> findAll(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClienteResponse> result = clienteService.findAll(query,estado,pageable);
        return ResponseEntity.ok(ApiResponse.success(result,"Clientes obtenidos correctamente"));
    }
}
