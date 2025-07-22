package com.backend.ticketing.controller;

import com.backend.ticketing.dto.TicketDTO;
import com.backend.ticketing.model.Ticket;
import com.backend.ticketing.service.TicketService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.any;

class TicketControllerTest {

    @InjectMocks
    private TicketController controller;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testIssueTicket() {
        TicketDTO dto = new TicketDTO();
        Ticket ticket = new Ticket();
        ticket.setCode("CODE");
        when(ticketService.issueTicketFromDTO(anyLong(), any(TicketDTO.class))).thenReturn(ticket);

        Response response = controller.issueTicket(1L, dto);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("CODE", response.getEntity());
    }

    @Test
    void testValidateTicket() {
        Ticket ticket = new Ticket();
        when(ticketService.validateTicket(2L)).thenReturn(ticket);

        Response response = controller.validateTicket(2L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ticket, response.getEntity());
    }
}