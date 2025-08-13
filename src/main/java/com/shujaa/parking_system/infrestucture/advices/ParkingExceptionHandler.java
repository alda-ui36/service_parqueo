package com.shujaa.parking_system.infrestucture.advices;

import com.shujaa.parking_system.application.dtos.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.access.AccessDeniedException;

@RestControllerAdvice
public class ParkingExceptionHandler {

    @ExceptionHandler(SPException.class)
    public ResponseEntity<ApiResponse<?>> handleSPException(SPException ex) {
        return ResponseEntity.status(400).body(ApiResponse.error(ex.getMessage(), 400));
    }
    // 1) Excepción de negocio específica
    @ExceptionHandler(ParkingNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleParkingNotFoundException(ParkingNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    // 2) JSON mal formado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String detalle = "Invalid JSON format: " + ex.getMostSpecificCause().getMessage();
        return ResponseEntity.badRequest().body(ApiResponse.error(detalle, HttpStatus.BAD_REQUEST.value()));
    }


    // 3) Errores de argumento (validaciones manuales)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    // 4) Captura cualquier otra excepción
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal error: " + ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    // 2.1) Errores de validación de @Valid
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

    // 1.1) Acceso denegado (403)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("Acceso denegado", HttpStatus.FORBIDDEN.value()));
    }

}
