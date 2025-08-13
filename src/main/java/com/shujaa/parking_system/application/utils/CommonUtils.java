package com.shujaa.parking_system.application.utils;

import com.shujaa.parking_system.infrestucture.advices.ParkingNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public class CommonUtils {

    public static <T> T findByIdOrThrow(Integer id, JpaRepository<T, Integer> repository, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new ParkingNotFoundException(entityName + " no encontrado"));
    }
    
    public static <T> void validateUniqueField(String fieldValue, String currentValue,
                                             JpaSpecificationExecutor<T> repository, 
                                             String fieldName, String entityName) {
        if (!fieldValue.equals(currentValue) && 
            repository.exists((root, cq, cb) -> cb.equal(root.get(fieldName), fieldValue))) {
            throw new ParkingNotFoundException("El " + fieldName + " ya está registrado");
        }
    }

} 