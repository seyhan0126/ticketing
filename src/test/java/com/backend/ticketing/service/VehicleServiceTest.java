package com.backend.ticketing.service;

import com.backend.ticketing.exception.DuplicateVehicleException;
import com.backend.ticketing.exception.VehicleNotFoundException;
import com.backend.ticketing.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;

public class VehicleServiceTest {

    private EntityManager em;
    private VehicleService service;

    @BeforeEach
    void setUp() {
        em = mock(EntityManager.class);
        service = new VehicleService();
        // Use reflection to inject mock EntityManager (since field is private)
        var emField = VehicleService.class.getDeclaredFields();
        for (var f : emField) {
            if (f.getType().equals(EntityManager.class)) {
                f.setAccessible(true);
                try {
                    f.set(service, em);
                } catch (Exception ignore) {}
            }
        }
    }

    @Test
    void createVehicle_shouldPersistVehicle() {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber("123ABC");
        v.setCapacity(10);

        doNothing().when(em).persist(v);

        Vehicle result = service.createVehicle(v);

        verify(em, times(1)).persist(v);
        assertEquals("123ABC", result.getRegistrationNumber());
    }

    @Test
    void createVehicle_shouldThrowDuplicateException() {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber("DUPL");

        PersistenceException pex = new PersistenceException(new RuntimeException("registrationNumber"));
        doThrow(pex).when(em).persist(v);

        assertThrows(DuplicateVehicleException.class, () -> service.createVehicle(v));
    }

    @Test
    void findByIdWithTickets_shouldReturnVehicle() {
        Vehicle v = new Vehicle();
        v.setId(99L);

        // Mock TypedQuery for the JPQL
        var query = mock(jakarta.persistence.TypedQuery.class);
        when(em.createQuery(anyString(), eq(Vehicle.class))).thenReturn(query);
        when(query.setParameter(eq("id"), eq(99L))).thenReturn(query);
        when(query.getSingleResult()).thenReturn(v);

        Vehicle result = service.findByIdWithTickets(99L);

        assertEquals(99L, result.getId());
    }

    @Test
    void findByIdWithTickets_shouldThrowNotFound() {
        var query = mock(jakarta.persistence.TypedQuery.class);
        when(em.createQuery(anyString(), eq(Vehicle.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        assertThrows(VehicleNotFoundException.class, () -> service.findByIdWithTickets(1L));
    }
}
