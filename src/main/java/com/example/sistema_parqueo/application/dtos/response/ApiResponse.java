package com.example.sistema_parqueo.application.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApiResponse <T>{
    private  String  mensaje;
    private T data;
    private  Integer code;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data, String mensaje){
        return  ApiResponse.<T>builder()
                .data(data)
                .mensaje(mensaje)
                .code(200)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String mensaje, int code){
        return ApiResponse.<T>builder()
                .mensaje(mensaje)
                .code(code)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
