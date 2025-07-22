package com.backend.ticketing.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DuplicateVehicleExceptionMapper implements ExceptionMapper<DuplicateVehicleException> {
    @Override
    public Response toResponse(DuplicateVehicleException e) {
        return Response.status(Response.Status.CONFLICT)
                .entity(new ErrorResponse("Duplicate Vehicle", e.getMessage()))
                .build();
    }
}