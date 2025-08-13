package com.shujaa.parking_system.application.service;

import com.shujaa.parking_system.application.dtos.projections.RegistroParqueoProjection;
import com.shujaa.parking_system.application.dtos.request.RegistroEntradaRequest;
import com.shujaa.parking_system.application.dtos.request.RegistroSalidaRequest;
import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import com.shujaa.parking_system.application.dtos.response.RegistroParqueoResponse;
import com.shujaa.parking_system.application.dtos.mapper.RegistroParqueoMapper;
import com.shujaa.parking_system.application.dtos.specification.RegistroParqueoSpecification;
import com.shujaa.parking_system.domain.port.input.IRegistroParqueoService;
import com.shujaa.parking_system.domain.port.output.IRegistroParqueoRepository;
import com.shujaa.parking_system.domain.port.output.IUsuarioRepository;
import com.shujaa.parking_system.infrestucture.advices.SPException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistroParqueoServiceImpl implements IRegistroParqueoService {

    private final IRegistroParqueoRepository registroParqueoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final RegistroParqueoMapper registroParqueoMapper;

    @Override
    public ApiResponse<RegistroParqueoProjection> registrarEntrada(RegistroEntradaRequest request) {
        String ticket = generarTicketUnico();
        LocalDateTime now = LocalDateTime.now();
        Integer userId = obtenerUsuarioAutenticadoId();

        try {
            // Ejecutar SP dentro de transacción aislada
            ejecutarRegistrarEntradaSP(request, ticket, now, userId);
        } catch (DataAccessException ex) {
            throw new SPException(extractSPMessage(ex));
        }

        // Ejecutar consulta fuera de @Transactional
        Optional<RegistroParqueoProjection> registro = registroParqueoRepository
                .findRegistroActivoByEspacio(request.getIdEspacio());

        return registro.map(r -> ApiResponse.success(r, "Registro de entrada creado exitosamente"))
                .orElseGet(() -> ApiResponse.error("No se pudo obtener el registro creado", 500));
    }

    private void ejecutarRegistrarEntradaSP(RegistroEntradaRequest req, String ticket, LocalDateTime now,
            Integer userId) {
        registroParqueoRepository.registrarEntrada(
                req.getDni(), req.getNombres(), req.getApePaterno(), req.getApeMaterno(),
                req.getTelefono(), req.getDireccion(), req.getCorreo(),
                req.getPlaca(), req.getMarca(), req.getColor(),
                req.getIdTipoVehiculo(), req.getIdEspacio(), req.getTarifaHora(),
                ticket, now, userId, now, now);
    }

    @Override
    public ApiResponse<RegistroParqueoProjection> registrarSalida(RegistroSalidaRequest request) {
        LocalDateTime now = LocalDateTime.now();
        Integer userId = obtenerUsuarioAutenticadoId();

        try {
            ejecutarRegistrarSalidaSP(request, now, userId);
        } catch (DataAccessException ex) {
            throw new SPException(extractSPMessage(ex));
        }

        return ApiResponse.success(null, "Registro de salida procesado exitosamente");
    }

    private void ejecutarRegistrarSalidaSP(RegistroSalidaRequest req, LocalDateTime now, Integer userId) {
        registroParqueoRepository.registrarSalida(
                req.getTicket(), now, req.getMontoTotal(), req.getIdMetodoPago(), userId, now);
    }

    private Integer obtenerUsuarioAutenticadoId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUsernameOrEmail(username, null)
                .orElseThrow(() -> new SPException("Usuario no encontrado"))
                .getId();
    }

    @Override
    public ApiResponse<RegistroParqueoProjection> obtenerRegistroActivo(Integer idEspacio) {
        Optional<RegistroParqueoProjection> registro = registroParqueoRepository
                .findRegistroActivoByEspacio(idEspacio);

        return registro.map(r -> ApiResponse.success(r, "Registro encontrado exitosamente"))
                .orElseGet(
                        () -> ApiResponse.error("No se encontró un registro activo para el espacio especificado", 404));
    }

    @Override
    public Page<RegistroParqueoResponse> findAllList(String query, Integer idMetodoPago,
                                                     LocalDateTime desde, LocalDateTime hasta,
                                                     String estado, Pageable pageable) {
        var spec = RegistroParqueoSpecification.filterBy(query, idMetodoPago, desde, hasta, estado);
        return registroParqueoRepository.findAll(spec, pageable)
                .map(registroParqueoMapper::toResponse);
    }


    @Override
    public RegistroParqueoResponse findById(Integer id) {
        var entity = registroParqueoRepository.findById(id)
                .orElseThrow(() -> new SPException("Registro de parqueo no encontrado"));
        return registroParqueoMapper.toResponse(entity);
    }

    private String generarTicketUnico() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String extractSPMessage(Throwable e) {
        Throwable t = e;
        while (t != null) {
            String m = t.getMessage();
            if (m != null) {
                if (m.contains("ERR001")) return "El espacio seleccionado ya está ocupado";
                if (m.contains("ERR002")) return "El ticket no es válido o ya fue procesado";
                if (m.contains("ERR003")) return "El vehículo ya tiene una entrada activa";
            }
            t = t.getCause();
        }
        return "Error interno al ejecutar procedimiento almacenado";
    }


}
