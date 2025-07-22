package com.backend.ticketing.service;

import com.backend.ticketing.dto.TicketDTO;
import com.backend.ticketing.model.Ticket;
import com.backend.ticketing.model.Vehicle;
import com.backend.ticketing.exception.TicketNotFoundException;
import com.backend.ticketing.exception.VehicleNotFoundException;
import com.backend.ticketing.mapper.EntityMapper;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;


@Stateless
public class TicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);

    @PersistenceContext
    private EntityManager em;

    public Ticket issueTicketFromDTO(Long vehicleId, TicketDTO dto) {
        Vehicle vehicle = em.find(Vehicle.class, vehicleId);
        if (vehicle == null) {
            log.warn("Vehicle not found when issuing ticket: {}", vehicleId);
            throw new VehicleNotFoundException(vehicleId);
        }

        Ticket ticket = EntityMapper.toTicket(dto, vehicle);
        ticket.setCode("TICKET-" + UUID.randomUUID()); // Optionally generate code here
        em.persist(ticket);
        log.info("Issued ticket {} for vehicle {}", ticket.getCode(), vehicleId);
        return ticket;
    }

    public Ticket validateTicket(Long ticketId) {
        Ticket ticket = em.find(Ticket.class, ticketId);
        if (ticket == null) {
            log.warn("Ticket not found: {}", ticketId);
            throw new TicketNotFoundException(ticketId);
        }
        if (ticket.isValidated()) {
            log.warn("Ticket already validated: {}", ticketId);
            throw new RuntimeException("Ticket already validated");
        }

        ticket.setValidated(true);
        ticket.setValidationTime(LocalDateTime.now());
        log.info("Validated ticket {}", ticketId);
        return ticket;
    }

    public List<Ticket> getTicketsByVehicleId(Long vehicleId) {
        log.info("Getting tickets for vehicle {}", vehicleId);
        return em.createQuery("SELECT t FROM Ticket t WHERE t.vehicle.id = :vehicleId", Ticket.class)
                .setParameter("vehicleId", vehicleId)
                .getResultList();
    }

    public List<Ticket> getAllTickets() {
        return em.createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
    }

}
