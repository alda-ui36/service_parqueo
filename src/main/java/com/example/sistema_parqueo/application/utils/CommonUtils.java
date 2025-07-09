package com.example.sistema_parqueo.application.utils;

import com.example.sistema_parqueo.infrestucture.advices.ParqueoNotfoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public class CommonUtils {

    public  static <T> T findByIdOrThrow(Integer id, JpaRepository<T, Integer> repository, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new ParqueoNotfoundException(entityName + " with ID " + id + " not found"));
    }

    public  static <T> void validateUniqueField(String fieldValue, String currentValue, JpaSpecificationExecutor<T> repository, String fieldName, String entityName) {
        if(!fieldValue.equals(currentValue) &&
            repository.exists((root, cq, cb ) -> cb.equal(root.get(fieldName),fieldValue))){
            throw new ParqueoNotfoundException("El " + fieldName + " '" + fieldValue + "' ya está en uso en " + entityName);
        }
    }
}
