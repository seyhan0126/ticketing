package com.backend.ticketing.controller;

import com.backend.ticketing.dto.VehicleDTO;
import com.backend.ticketing.model.Vehicle;
import com.backend.ticketing.mapper.EntityMapper;
import com.backend.ticketing.service.VehicleService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/vehicles")
@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Vehicles", description = "Operations related to vehicle registration and management")
public class VehicleController {
    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    @EJB
    private VehicleService vehicleService;

    @POST
    @Operation(summary = "Register a new vehicle")
    public Response registerVehicle(VehicleDTO dto) {
        Vehicle vehicle = EntityMapper.toVehicle(dto);
        Vehicle savedVehicle = vehicleService.createVehicle(vehicle);
        return Response.status(Response.Status.CREATED).entity(savedVehicle.getId()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a vehicle by ID")
    public Response getVehicleById(@PathParam("id") Long id) {
        try {
            Vehicle vehicle = vehicleService.findByIdWithTickets(id);
            return Response.ok(vehicle).build();
        } catch (NotFoundException e) {
            log.info("Vehicle not found: {}", id);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            log.error("Error getting vehicle", e);
            return Response.serverError().entity("Error getting vehicle").build();
        }
    }
}
