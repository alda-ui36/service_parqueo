package com.shujaa.parking_system.domain.port.output;

import com.shujaa.parking_system.application.dtos.projections.RegistroParqueoProjection;
import com.shujaa.parking_system.domain.entity.RegistroParqueo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface IRegistroParqueoRepository extends JpaRepository<RegistroParqueo, Integer>, JpaSpecificationExecutor<RegistroParqueo> {

        @Query(value = "CALL sp_registrar_entrada_parqueo(" +
                        ":p_dni, :p_nombres, :p_ape_paterno, :p_ape_materno, " +
                        ":p_telefono, :p_direccion, :p_correo, :p_placa, :p_marca, :p_color, " +
                        ":p_id_tipo_vehiculo, :p_id_espacio, :p_tarifa_hora, " +
                        ":p_ticket, :p_hora_ingreso, :p_creado_por, :p_creado_en, :p_actualizado_en)", nativeQuery = true)
        Object[] registrarEntrada(
                        @Param("p_dni") String dni,
                        @Param("p_nombres") String nombres,
                        @Param("p_ape_paterno") String apePaterno,
                        @Param("p_ape_materno") String apeMaterno,
                        @Param("p_telefono") String telefono,
                        @Param("p_direccion") String direccion,
                        @Param("p_correo") String correo,
                        @Param("p_placa") String placa,
                        @Param("p_marca") String marca,
                        @Param("p_color") String color,
                        @Param("p_id_tipo_vehiculo") Integer idTipoVehiculo,
                        @Param("p_id_espacio") Integer idEspacio,
                        @Param("p_tarifa_hora") BigDecimal tarifaHora,
                        @Param("p_ticket") String ticket,
                        @Param("p_hora_ingreso") LocalDateTime horaIngreso,
                        @Param("p_creado_por") Integer creadoPor,
                        @Param("p_creado_en") LocalDateTime creadoEn,
                        @Param("p_actualizado_en") LocalDateTime actualizadoEn);

        @Query(value = "CALL sp_registrar_salida_parqueo(" +
                        ":p_ticket, :p_hora_salida, :p_monto_total, :p_id_metodo_pago, :p_actualizado_por, :p_actualizado_en)", nativeQuery = true)
        Object[] registrarSalida(
                        @Param("p_ticket") String ticket,
                        @Param("p_hora_salida") LocalDateTime horaSalida,
                        @Param("p_monto_total") BigDecimal montoTotal,
                        @Param("p_id_metodo_pago") Integer idMetodoPago,
                        @Param("p_actualizado_por") Integer actualizadoPor,
                        @Param("p_actualizado_en") LocalDateTime actualizadoEn);

        @Query(value = "CALL sp_obtener_registro_activo_por_espacio(:idEspacio)", nativeQuery = true)
        Optional<RegistroParqueoProjection> findRegistroActivoByEspacio(@Param("idEspacio") Integer idEspacio);

    @EntityGraph(attributePaths = {
        "cliente",
        "vehiculo",
        "vehiculo.tipoVehiculo",
        "vehiculo.cliente",
        "espacio",
        "espacio.zona",
        "espacio.tipoVehiculo",
        "metodoPago",
        "creadoPor",
        "actualizadoPor"
    })
    List<RegistroParqueo> findAll(Specification<RegistroParqueo> spec);

}