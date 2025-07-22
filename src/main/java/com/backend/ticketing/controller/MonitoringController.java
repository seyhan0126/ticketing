package com.backend.ticketing.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Path("/monitor")
@ApplicationScoped
@Tag(name = "Monitoring", description = "System health and metrics")
public class MonitoringController {
    private static final Logger log = LoggerFactory.getLogger(MonitoringController.class);

    @PersistenceContext
    private EntityManager em;

    @GET
    @Path("/health")
    @Operation(summary = "Health check", description = "Checks database connection and returns status.")
    public Response healthCheck() {
        try {
            em.createQuery("SELECT 1").getSingleResult();
            return Response.ok("OK").build();
        } catch (Exception e) {
            log.error("Health check failed: {}", e.getMessage());
            return Response.serverError().entity("DB ERROR").build();
        }
    }

    @GET
    @Path("/metrics")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Application metrics", description = "Returns basic ticketing/vehicle metrics.")
    public Response metrics() {
        try {
            long totalTickets = (long) em.createQuery("SELECT COUNT(t) FROM Ticket t").getSingleResult();
            long validatedTickets = (long) em.createQuery("SELECT COUNT(t) FROM Ticket t WHERE t.validated = true").getSingleResult();
            long vehicles = (long) em.createQuery("SELECT COUNT(v) FROM Vehicle v").getSingleResult();

            return Response.ok(Map.of(
                    "totalTickets", totalTickets,
                    "validatedTickets", validatedTickets,
                    "vehicles", vehicles
            )).build();
        } catch (Exception e) {
            log.error("Metrics fetch failed: {}", e.getMessage());
            return Response.serverError().entity("Error fetching metrics").build();
        }
    }
}
