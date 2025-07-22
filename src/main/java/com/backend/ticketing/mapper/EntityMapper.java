package com.backend.ticketing.mapper;

import com.backend.ticketing.dto.TicketDTO;
import com.backend.ticketing.dto.VehicleDTO;
import com.backend.ticketing.model.Ticket;
import com.backend.ticketing.model.Vehicle;

import java.time.LocalDateTime;

public class EntityMapper {
    public static Vehicle toVehicle(VehicleDTO dto) {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber(dto.getRegistrationNumber());
        v.setType(dto.getType());
        v.setCapacity(dto.getCapacity());
        return v;
    }

    public static Ticket toTicket(TicketDTO dto, Vehicle vehicle) {
        Ticket t = new Ticket();
        t.setPassengerName(dto.getPassengerName());
        t.setVehicle(vehicle);
        t.setTimestamp(LocalDateTime.now());
        return t;
    }
}
