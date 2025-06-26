package com.example.sistema_parqueo.domain.port.output;

import com.example.sistema_parqueo.domain.entity.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
} 