package com.example.sistema_parqueo.infrestucture.advices;

public class ParqueoNotfoundException extends RuntimeException{
    public ParqueoNotfoundException(String mensaje) {
        super(mensaje);
    }
}
