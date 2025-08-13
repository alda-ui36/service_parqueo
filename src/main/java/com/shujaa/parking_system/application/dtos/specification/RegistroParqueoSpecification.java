package com.shujaa.parking_system.application.dtos.specification;

import com.shujaa.parking_system.domain.entity.RegistroParqueo;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class RegistroParqueoSpecification {

    public static Specification<RegistroParqueo> filterBy(String query, Integer idMetodoPago, LocalDateTime desde, LocalDateTime hasta, String estado) {
        return (root, cq, cb) -> {
            var predicates = cb.conjunction();

            Join<?, ?> cliente = root.join("cliente", JoinType.LEFT);
            Join<?, ?> vehiculo = root.join("vehiculo", JoinType.LEFT);
            Join<?, ?> tipoVehiculo = vehiculo.join("tipoVehiculo", JoinType.LEFT);

            if (query != null && !query.isBlank()) {
                String likeQuery = "%" + query.toLowerCase() + "%";

                predicates = cb.and(predicates, cb.or(
                        cb.like(cb.lower(root.get("ticket")), likeQuery),
                        cb.like(cb.lower(cliente.get("nombres")), likeQuery),
                        cb.like(cb.lower(cliente.get("apePaterno")), likeQuery),
                        cb.like(cb.lower(cliente.get("apeMaterno")), likeQuery),
                        cb.like(cb.lower(cb.concat(
                                cb.concat(
                                        cb.concat(cliente.get("nombres"), cb.literal(" ")),
                                        cliente.get("apePaterno")),
                                cb.concat(cb.literal(" "), cliente.get("apeMaterno"))
                        )), likeQuery),
                        cb.like(cb.lower(cliente.get("dni")), likeQuery),
                        cb.like(cb.lower(vehiculo.get("placa")), likeQuery),
                        cb.like(cb.lower(vehiculo.get("marca")), likeQuery),
                        cb.like(cb.lower(tipoVehiculo.get("nombre")), likeQuery)
                ));
            }

            if (idMetodoPago != null) {
                predicates = cb.and(predicates, cb.equal(root.get("metodoPago").get("id"), idMetodoPago));
            }

            if (desde != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("horaIngreso"), desde));
            }

            if (hasta != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("horaIngreso"), hasta));
            }

            if (estado != null && !estado.isBlank()) {
                predicates = cb.and(predicates, cb.equal(root.get("estadoPago"), estado));
            }

            return predicates;
        };
    }

    public static Specification<RegistroParqueo> fechaIngresoBetween(LocalDateTime inicio, LocalDateTime fin) {
        return (root, query, cb) -> {
            if (inicio != null && fin != null) {
                return cb.between(root.get("horaIngreso"), inicio, fin);
            } else if (inicio != null) {
                return cb.greaterThanOrEqualTo(root.get("horaIngreso"), inicio);
            } else if (fin != null) {
                return cb.lessThanOrEqualTo(root.get("horaIngreso"), fin);
            }
            return cb.conjunction();
        };
    }
}
