package com.shujaa.parking_system.application.service;

import com.shujaa.parking_system.application.dtos.projections.RegistroParqueoProjection;
import com.shujaa.parking_system.application.dtos.request.RegistroEntradaRequest;
import com.shujaa.parking_system.application.dtos.request.RegistroSalidaRequest;
import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import com.shujaa.parking_system.domain.port.input.IRegistroParqueoService;
import com.shujaa.parking_system.domain.port.output.IRegistroParqueoRepository;
import com.shujaa.parking_system.domain.port.output.IUsuarioRepository;
import com.shujaa.parking_system.infrestucture.advices.SPExcepetion;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistroParqueoServiceImpl implements IRegistroParqueoService {

    private final IRegistroParqueoRepository registroParqueoRepository;
    private final IUsuarioRepository usuarioRepository;


    @Override
    public ApiResponse<RegistroParqueoProjection> registrarEntrada(RegistroEntradaRequest request) {
        String ticket = generarTicketUnico();
        LocalDateTime now = LocalDateTime.now();
        Integer userId = obtenerUsuarioAutenticado();
        try{
            ejecutarRegistroEntrada(request,ticket,now,userId);
        }catch (DataAccessException ex){
            throw  new SPExcepetion(extracSPMessage(ex));
        }
        Optional<RegistroParqueoProjection> registro = registroParqueoRepository.findRegitroActivoByEspacio(request.getIdEspacio());
        return registro.map(r -> ApiResponse.success(r,"registro entrada exitoso"))
                .orElseGet(() -> ApiResponse.error("No se pudo registrar la entrada", 500));
    }

    private void  ejecutarRegistroEntrada(RegistroEntradaRequest req, String ticket,
                                          LocalDateTime now, Integer userId){
        registroParqueoRepository.registrarEntrada(
                req.getDni(),
                req.getNombres(),
                req.getApePaterno(),
                req.getApeMaterno(),
                req.getTelefono(),
                req.getDireccion(),
                req.getCorreo(),
                req.getPlaca(),
                req.getMarca(),
                req.getColor(),
                req.getIdTipoVehiculo(),
                req.getIdEspacio(),
                req.getTarifaHora(),
                ticket,now,userId,now,now);
    }
    @Override
    public ApiResponse<RegistroParqueoProjection> registrarSalida(RegistroSalidaRequest request) {
        LocalDateTime now = LocalDateTime.now();
        Integer userId = obtenerUsuarioAutenticado();
        try {
            ejecutarRegistroSalida(request, now, userId);
        } catch (DataAccessException ex) {
            throw new SPExcepetion(extracSPMessage(ex));
        }

        return ApiResponse.success(null, "Registro de salida exitoso");
    }

    private void ejecutarRegistroSalida(RegistroSalidaRequest req, LocalDateTime now, Integer userId){
        registroParqueoRepository.registrarSalida(
                req.getTicket(),
                now,
                req.getMontoTotal(),
                req.getIdMetodoPago(),
                userId,
                now
        );
    }

    @Override
    public ApiResponse<RegistroParqueoProjection> obtenerRegistroActivo(Integer idEspacio) {
        Optional<RegistroParqueoProjection> registro  = registroParqueoRepository.findRegitroActivoByEspacio(idEspacio);

        return registro.map(r -> ApiResponse.success(r,"registro encontrado"))
                .orElseGet(() -> ApiResponse.error("No se encontró un registro activo para el espacio", 404));
    }



    private Integer obtenerUsuarioAutenticado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUsernameOrEmail(username, null)
                .orElseThrow(() -> new SPExcepetion("usuario no encontrado")).getId();
    }

    private String generarTicketUnico(){
        return "TKT-"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

    private String extracSPMessage(Throwable e){
        Throwable t = e;
        while(t != null){
            String m = t.getMessage();
            if(m  != null){
                if(m.contains("ERR001")) return "El espacio ya está ocupado por otro vehículo";
                if(m.contains("ERR002")) return "El ticket ya ha sido dado de salida";
                if(m.contains("ERR003")) return "El vehiculo ya tiene un registro activo";
            }
        t = t.getCause();

        }
        return "Eror en el Procedimiento Almacenado";
    }
}
