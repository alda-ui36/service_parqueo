package com.example.sistema_parqueo.infrestucture.advices;

import com.example.sistema_parqueo.application.dtos.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ParqueoExceptionHandler {

    @ExceptionHandler(ParqueoNotfoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleParqueoNotFoundException(ParqueoNotfoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String detalle = "json invalido: "+ex.getMostSpecificCause() + ex.getMessage();
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(detalle, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        String detalle = "Error interno del servidor: " + ex.getMessage();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(detalle, HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            sb.append("El campo '")
              .append(error.getField())
              .append("': ")
              .append(error.getDefaultMessage())
              .append(". ");
        });
        return ResponseEntity.badRequest().body(ApiResponse.error(sb.toString().trim(), HttpStatus.BAD_REQUEST.value()));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("Acceso denegado: " + ex.getMessage(), HttpStatus.FORBIDDEN.value()));
    }
}
