package com.backend.ticketing.model;

import com.backend.ticketing.enumeration.VehicleType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleEntityTest {

    @Test
    void testVehicleCreation() {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("BUS-42");
        vehicle.setCapacity(50);
        vehicle.setType(VehicleType.BUS);

        assertEquals("BUS-42", vehicle.getRegistrationNumber());
        assertEquals(50, vehicle.getCapacity());
        assertEquals(VehicleType.BUS, vehicle.getType());
    }
}