package com.backend.ticketing.service;

import com.backend.ticketing.dto.TicketDTO;
import com.backend.ticketing.exception.TicketNotFoundException;
import com.backend.ticketing.model.Ticket;
import com.backend.ticketing.model.Vehicle;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;

class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private EntityManager em;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void issueTicketFromDTO_shouldPersistTicket() {
        TicketDTO dto = new TicketDTO();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        when(em.find(Vehicle.class, 1L)).thenReturn(vehicle);

        TicketService spyService = Mockito.spy(ticketService);
        Ticket result = spyService.issueTicketFromDTO(1L, dto);

        assertNotNull(result.getCode());
        verify(em).persist(any(Ticket.class));
    }

    @Test
    void validateTicket_shouldSetValidatedTrue() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setValidated(false);
        when(em.find(Ticket.class, 1L)).thenReturn(ticket);

        Ticket result = ticketService.validateTicket(1L);
        assertTrue(result.isValidated());
        assertNotNull(result.getValidationTime());
    }

    @Test
    void validateTicket_shouldThrowIfNotFound() {
        when(em.find(Ticket.class, 1L)).thenReturn(null);
        assertThrows(TicketNotFoundException.class, () -> ticketService.validateTicket(1L));
    }

    @Test
    void getAllTickets_shouldReturnList() {
        List<Ticket> list = Collections.singletonList(new Ticket());
        when(em.createQuery(anyString(), eq(Ticket.class))).thenReturn(new DummyTypedQuery<>(list));
        List<Ticket> result = ticketService.getAllTickets();
        assertEquals(1, result.size());
    }

}

