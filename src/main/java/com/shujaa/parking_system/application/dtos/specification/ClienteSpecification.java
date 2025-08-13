package com.shujaa.parking_system.application.dtos.specification;

import com.shujaa.parking_system.domain.entity.Cliente;
import org.springframework.data.jpa.domain.Specification;

public class ClienteSpecification {
    public static Specification<Cliente> filterBy(String query, Boolean estado) {
        return (root, cq, cb) -> {
            var predicates = cb.conjunction();
            if (query != null && !query.isBlank()) {
                String likeQuery = "%" + query.toLowerCase() + "%";
                predicates = cb.and(predicates, cb.or(
                        cb.like(cb.lower(root.get("nombres")), likeQuery),
                        cb.like(cb.lower(root.get("apePaterno")), likeQuery),
                        cb.like(cb.lower(root.get("apeMaterno")), likeQuery),
                        cb.like(cb.lower(root.get("dni")), likeQuery),
                        cb.like(cb.lower(root.get("telefono")), likeQuery),
                        cb.like(cb.lower(root.get("direccion")), likeQuery)
                ));
            }
            if (estado != null) {
                predicates = cb.and(predicates, cb.equal(root.get("estado"), estado));
            }
            return predicates;
        };
    }
} 