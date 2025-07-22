package com.backend.ticketing.exception;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(Long id) {
        super("Vehicle with ID " + id + " not found.");
    }
}
