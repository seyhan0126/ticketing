package com.backend.ticketing.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class TicketEntityTest {

    @Test
    void testTicketCreation() {
        Ticket ticket = new Ticket();
        ticket.setCode("ABC123");
        ticket.setPassengerName("John Doe");
        ticket.setTimestamp(LocalDateTime.now());
        ticket.setValidated(false);

        assertEquals("ABC123", ticket.getCode());
        assertEquals("John Doe", ticket.getPassengerName());
        assertFalse(ticket.isValidated());
    }
}