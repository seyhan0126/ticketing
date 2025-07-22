package com.backend.ticketing.controller;

import com.backend.ticketing.dto.TicketDTO;
import com.backend.ticketing.model.Ticket;
import com.backend.ticketing.service.TicketService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/ticket")
@Produces("application/json")
@Consumes("application/json")
@Tag(name = "Tickets", description = "Ticket operations")
public class TicketController {
    private static final Logger log = LoggerFactory.getLogger(TicketController.class);

    @Inject
    private TicketService ticketService;

    @POST
    @Path("/issue/{vehicleId}")
    @Operation(summary = "Issue a ticket", description = "Issues a new ticket for the given vehicle")
    public Response issueTicket(@PathParam("vehicleId") Long vehicleId, TicketDTO dto) {
        log.info("Issuing ticket for vehicleId={}", vehicleId);
        Ticket ticket = ticketService.issueTicketFromDTO(vehicleId, dto);
        return Response.status(Response.Status.CREATED).entity(ticket.getCode()).build();
    }

    @PUT
    @Path("/validate/{ticketId}")
    @Operation(summary = "Validate a ticket", description = "Validates an existing ticket")
    public Response validateTicket(@PathParam("ticketId") Long ticketId) {
        log.info("Validating ticketId={}", ticketId);
        Ticket ticket = ticketService.validateTicket(ticketId);
        return Response.ok(ticket).build();
    }

    @GET
    @Path("/vehicle/{vehicleId}")
    @Operation(summary = "Get tickets by vehicle", description = "Lists all tickets for a vehicle")
    public Response getTicketsByVehicle(@PathParam("vehicleId") Long vehicleId) {
        log.info("Getting tickets for vehicleId={}", vehicleId);
        return Response.ok(ticketService.getTicketsByVehicleId(vehicleId)).build();
    }

    @GET
    @Path("/all")
    @Operation(summary = "List all tickets", description = "Lists all tickets in the system")
    public Response getAllTickets() {
        return Response.ok(ticketService.getAllTickets()).build();
    }
}
