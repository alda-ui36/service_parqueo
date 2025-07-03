package com.example.sistema_parqueo.application.utils;

import com.example.sistema_parqueo.domain.entity.Espacio;
import com.example.sistema_parqueo.domain.entity.TipoVehiculo;
import com.example.sistema_parqueo.domain.entity.Zona;
import com.example.sistema_parqueo.domain.port.output.IEspacioRepository;
import com.example.sistema_parqueo.domain.port.output.ITipoVehiculoRepository;
import com.example.sistema_parqueo.domain.port.output.IZonaRepository;
import com.example.sistema_parqueo.infrestucture.advices.ParqueoNotfoundException;

public class EspacioUtils {

    public static Zona getZonaById(Integer id, IZonaRepository zonaRepository){
        return zonaRepository.findById(id)
                .orElseThrow(() -> new ParqueoNotfoundException("Zona no encontrada" + id));
    }

    public static TipoVehiculo gettipoVehiculoById(Integer id, ITipoVehiculoRepository tipoVehiculoRepository) {
        return tipoVehiculoRepository.findById(id)
                .orElseThrow(() -> new ParqueoNotfoundException("Tipo de Vehículo no encontrado" + id));
    }

    public static Espacio getEspacioById(Integer id, IEspacioRepository espacioRepository) {
        return espacioRepository.findById(id)
                .orElseThrow(() -> new ParqueoNotfoundException("Espacio no encontrado" + id));
    }

    public static void validarCupoDisponible(Zona zona, IEspacioRepository espacioRepository) {
        if (espacioRepository.countByZonaId(zona.getId()) >= zona.getTotalEspacios()) {
            throw new ParqueoNotfoundException("No hay cupos disponibles en la zona: " );
        }
    }
    public static void  validarEspacioUnico(Integer zonaId, String numeroEspacio, String currentNumero, IEspacioRepository espacioRepository) {
        if (!numeroEspacio.equals(currentNumero) &&
        espacioRepository.existsByZonaIdAndNumeroEspacio(zonaId, numeroEspacio)) {
            throw new ParqueoNotfoundException("El número de espacio ya existe en la zona: " + zonaId);
        }
    }
}
