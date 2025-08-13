package com.shujaa.parking_system.domain.port.output;

import com.shujaa.parking_system.domain.entity.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEspacioRepository extends JpaRepository<Espacio, Integer>, JpaSpecificationExecutor<Espacio> {
    long countByZonaId(Integer zonaId);
    boolean existsByZonaIdAndNumeroEspacio(Integer zonaId, String numeroEspacio);
}