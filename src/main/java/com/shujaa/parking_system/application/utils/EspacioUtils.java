package com.shujaa.parking_system.application.utils;

import com.shujaa.parking_system.domain.entity.Espacio;
import com.shujaa.parking_system.domain.entity.TipoVehiculo;
import com.shujaa.parking_system.domain.entity.Zona;
import com.shujaa.parking_system.domain.port.output.IEspacioRepository;
import com.shujaa.parking_system.domain.port.output.ITipoVehiculoRepository;
import com.shujaa.parking_system.domain.port.output.IZonaRepository;
import com.shujaa.parking_system.infrestucture.advices.ParkingNotFoundException;

public class EspacioUtils {
    public static Zona getZonaById(Integer id, IZonaRepository zonaRepository) {
        return zonaRepository.findById(id)
                .orElseThrow(() -> new ParkingNotFoundException("Zona no encontrada"));
    }

    public static TipoVehiculo getTipoVehiculoById(Integer id, ITipoVehiculoRepository tipoVehiculoRepository) {
        return tipoVehiculoRepository.findById(id)
                .orElseThrow(() -> new ParkingNotFoundException("Tipo de vehículo no encontrado"));
    }

    public static Espacio getEspacioById(Integer id, IEspacioRepository espacioRepository) {
        return espacioRepository.findById(id)
                .orElseThrow(() -> new ParkingNotFoundException("Espacio no encontrado"));
    }

    public static void validateCupoDisponible(Zona zona, IEspacioRepository espacioRepository) {
        if (espacioRepository.countByZonaId(zona.getId()) >= zona.getTotalEspacios()) {
            throw new IllegalArgumentException("No hay cupo disponible en la zona");
        }
    }

    public static void validateNumeroEspacioUnico(Integer zonaId, String numeroEspacio, String currentNumero, IEspacioRepository espacioRepository) {
        if (!numeroEspacio.equals(currentNumero) &&
                espacioRepository.existsByZonaIdAndNumeroEspacio(zonaId, numeroEspacio)) {
            throw new IllegalArgumentException("El número de espacio ya existe en la zona");
        }
    }
} 