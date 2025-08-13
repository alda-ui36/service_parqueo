package com.shujaa.parking_system.infrestucture.advices;

public class ParkingNotFoundException extends RuntimeException {
    public ParkingNotFoundException(String mensaje) {
        super(mensaje);
    }
}