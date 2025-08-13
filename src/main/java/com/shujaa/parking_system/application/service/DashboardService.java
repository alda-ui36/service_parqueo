package com.shujaa.parking_system.application.service;

import com.shujaa.parking_system.application.dtos.response.DashboardStatsResponse;
import com.shujaa.parking_system.application.dtos.specification.RegistroParqueoSpecification;
import com.shujaa.parking_system.domain.entity.RegistroParqueo;
import com.shujaa.parking_system.domain.port.output.IRegistroParqueoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IRegistroParqueoRepository registroParqueoRepository;

    public DashboardStatsResponse obtenerEstadisticas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        Specification<RegistroParqueo> spec =
                RegistroParqueoSpecification.fechaIngresoBetween(fechaInicio, fechaFin);

        List<RegistroParqueo> registros = registroParqueoRepository.findAll(spec);

        // Agrupación por mes
        Map<Month, List<RegistroParqueo>> registrosPorMes = registros.stream()
                .collect(Collectors.groupingBy(r -> r.getHoraIngreso().getMonth()));

        List<DashboardStatsResponse.MonthlyData> monthlyDataList = Arrays.stream(Month.values())
                .map(month -> {
                    List<RegistroParqueo> lista = registrosPorMes.getOrDefault(month, Collections.emptyList());
                    Long totalReservas = (long) lista.size();
                    BigDecimal montoTotal = lista.stream()
                            .map(r -> Optional.ofNullable(r.getMontoTotal()).orElse(BigDecimal.ZERO))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return DashboardStatsResponse.MonthlyData.builder()
                            .month(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH))
                            .totalReservas(totalReservas)
                            .montoTotal(montoTotal)
                            .build();
                })
                .collect(Collectors.toList());

        // Lista de todos los métodos de pago con conteo
        List<DashboardStatsResponse.MetodoPagoData> metodosPagoStats = registros.stream()
                .filter(r -> r.getMetodoPago() != null)
                .collect(Collectors.groupingBy(r -> r.getMetodoPago().getNombre(), Collectors.counting()))
                .entrySet().stream()
                .map(e -> new DashboardStatsResponse.MetodoPagoData(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(DashboardStatsResponse.MetodoPagoData::getCantidadUsos).reversed())
                .collect(Collectors.toList());

        return DashboardStatsResponse.builder()
                .monthlyStats(monthlyDataList)
                .metodosPagoStats(metodosPagoStats)
                .build();
    }
}
