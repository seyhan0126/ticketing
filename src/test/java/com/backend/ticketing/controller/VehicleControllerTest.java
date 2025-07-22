package com.backend.ticketing.controller;

import com.backend.ticketing.dto.VehicleDTO;
import com.backend.ticketing.model.Vehicle;
import com.backend.ticketing.service.VehicleService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

class VehicleControllerTest {

    private VehicleController controller;
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        controller = new VehicleController();
        vehicleService = mock(VehicleService.class);

        // Inject mock service using reflection
        var fields = VehicleController.class.getDeclaredFields();
        for (var f : fields) {
            if (f.getType().equals(VehicleService.class)) {
                f.setAccessible(true);
                try {
                    f.set(controller, vehicleService);
                } catch (Exception ignore) {}
            }
        }
    }

    @Test
    void registerVehicle_shouldReturnCreated() {
        VehicleDTO dto = new VehicleDTO();
        dto.setRegistrationNumber("ZZ-TEST");
        Vehicle saved = new Vehicle();
        saved.setId(123L);

        when(vehicleService.createVehicle(any())).thenReturn(saved);

        Response response = controller.registerVehicle(dto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(123L, response.getEntity());
        verify(vehicleService).createVehicle(any());
    }

    @Test
    void getVehicleById_shouldReturnOk() {
        Vehicle v = new Vehicle();
        v.setId(77L);
        when(vehicleService.findByIdWithTickets(77L)).thenReturn(v);

        Response response = controller.getVehicleById(77L);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(v, response.getEntity());
    }

    @Test
    void getVehicleById_shouldReturnNotFound() {
        when(vehicleService.findByIdWithTickets(77L)).thenThrow(new jakarta.ws.rs.NotFoundException("not found"));

        Response response = controller.getVehicleById(77L);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("not found", response.getEntity());
    }
}
