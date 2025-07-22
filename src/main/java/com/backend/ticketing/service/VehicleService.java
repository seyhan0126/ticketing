package com.backend.ticketing.service;


import com.backend.ticketing.model.Vehicle;
import com.backend.ticketing.exception.DuplicateVehicleException;
import com.backend.ticketing.exception.VehicleNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);

    @PersistenceContext
    private EntityManager em;

    public Vehicle createVehicle(Vehicle vehicle) {
        try {
            em.persist(vehicle);
            log.info("Created vehicle with registrationNumber={}", vehicle.getRegistrationNumber());
            return vehicle;
        } catch (PersistenceException e) {
            if (e.getCause() != null && e.getCause().getMessage().contains("registrationNumber")) {
                log.warn("Duplicate vehicle registration: {}", vehicle.getRegistrationNumber());
                throw new DuplicateVehicleException("Vehicle with registration number '" + vehicle.getRegistrationNumber() + "' already exists.");
            }
            throw e;
        }
    }

    public Vehicle findByIdWithTickets(Long id) {
        try {
            log.info("Fetching vehicle with tickets, id={}", id);
            return em.createQuery(
                            "SELECT v FROM Vehicle v LEFT JOIN FETCH v.tickets WHERE v.id = :id", Vehicle.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.warn("Vehicle not found: {}", id);
            throw new VehicleNotFoundException(id);
        }
    }
}
