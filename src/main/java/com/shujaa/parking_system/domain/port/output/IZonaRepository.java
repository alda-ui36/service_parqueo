package com.shujaa.parking_system.domain.port.output;

import com.shujaa.parking_system.domain.entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IZonaRepository extends JpaRepository<Zona, Integer>, JpaSpecificationExecutor<Zona> {
} 