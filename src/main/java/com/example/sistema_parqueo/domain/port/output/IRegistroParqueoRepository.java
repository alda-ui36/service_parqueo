package com.example.sistema_parqueo.domain.port.output;

import com.example.sistema_parqueo.domain.entity.RegistroParqueo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistroParqueoRepository extends JpaRepository<RegistroParqueo, Long> {

}