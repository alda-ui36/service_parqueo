package com.shujaa.parking_system.domain.port.output;

import com.shujaa.parking_system.domain.entity.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoVehiculoRepository extends JpaRepository<TipoVehiculo, Integer> , JpaSpecificationExecutor<TipoVehiculo> {
} 