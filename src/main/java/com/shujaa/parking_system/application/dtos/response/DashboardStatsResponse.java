package com.shujaa.parking_system.application.dtos.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardStatsResponse {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MonthlyData {
        private String month; // Ej: "January"
        private Long totalReservas;
        private BigDecimal montoTotal;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MetodoPagoData {
        private String metodo;
        private Long cantidadUsos;
    }

    private List<MonthlyData> monthlyStats;
    private List<MetodoPagoData> metodosPagoStats; // Lista en lugar de uno solo
}
