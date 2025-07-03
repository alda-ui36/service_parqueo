package com.example.sistema_parqueo.domain.port.output;

import com.example.sistema_parqueo.domain.entity.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface IEspacioRepository extends JpaRepository<Espacio, Integer>, JpaSpecificationExecutor<Espacio> {
    long countByZonaId(Integer zonaId);

    boolean existsByZonaIdAndNumeroEspacio(Integer zonaId, String numeroEspacio);
}