package com.backend.ticketing.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class VehicleNotFoundExceptionMapper implements ExceptionMapper<VehicleNotFoundException> {
    @Override
    public Response toResponse(VehicleNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse("Vehicle Not Found", ex.getMessage()))
                .build();
    }
}
