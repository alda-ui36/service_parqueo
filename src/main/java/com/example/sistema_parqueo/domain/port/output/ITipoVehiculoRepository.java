package com.example.sistema_parqueo.domain.port.output;

import com.example.sistema_parqueo.domain.entity.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoVehiculoRepository extends JpaRepository<TipoVehiculo, Integer> , JpaSpecificationExecutor<TipoVehiculo> {
} 