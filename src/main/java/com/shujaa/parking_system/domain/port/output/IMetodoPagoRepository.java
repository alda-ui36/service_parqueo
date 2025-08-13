package com.shujaa.parking_system.domain.port.output;

import com.shujaa.parking_system.domain.entity.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
} 