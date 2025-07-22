Vehicle & Ticketing Demo Project

A Java Jakarta EE demo for managing vehicles and issuing/validating tickets, complete with health/metrics monitoring, RESTful APIs, Docker Compose for local development, and PostgreSQL as the data backend.

Overview

This project is a backend demo simulating a public transport ticketing system. It allows you to register vehicles, issue tickets to passengers, validate tickets, and monitor system health and statistics via REST endpoints.

Features

Vehicle registration (bus, tram, metro, train, ferry)

Ticket issuing & validation per vehicle

Retrieve all tickets for a vehicle

Basic health check & operational metrics

PostgreSQL persistence

Dockerized setup for easy development and testing

WildFly application server integration

Architecture


Jakarta EE (JAX-RS, JPA, EJB/Stateless Beans)

REST API for all business operations

PostgreSQL as database

WildFly as application server (Dockerized)

Docker Compose for orchestration

API Endpoints

Monitoring

GET /monitor/health

Check application/database health

Response: OK or DB ERROR

GET /monitor/metrics

Get statistics (total tickets, validated tickets, vehicles)


Vehicles
POST /vehicles
Register new vehicle
Body: VehicleDTO
Response: Vehicle ID

GET /vehicles/{id}
Get vehicle by ID, including its tickets

Tickets
POST /ticket/issue/{vehicleId}
Issue ticket for a vehicle
Body: TicketDTO
Response: Ticket code

PUT /ticket/validate/{ticketId}
Validate ticket
Response: Ticket (updated)

GET /ticket/vehicle/{vehicleId}
Get all tickets for a vehicle

Getting Started

Requirements
Java 17+
Maven 3.6+
Docker & Docker Compose

Running Locally
Clone the repo

git clone https://github.com/seyhan0126/ticketing.git

cd ticketing

Build the application

mvn clean package

Start services

docker-compose up --build

The backend will be available at http://localhost:8080/

Usage Examples
 
  See the /api endpoints above for routes. Use Postman, curl, or any REST client.

Register Vehicle

POST /vehicles
{
  "registrationNumber": "B1234XYZ",
  "type": "BUS",
  "capacity": 40
}

Issue Ticket

POST /ticket/issue/1
{
  "passengerName": "Alice"
}

Validate Ticket

PUT /ticket/validate/1

Entities
Vehicle
Fields: id, registrationNumber, type, capacity, tickets

Ticket
Fields: id, code, passengerName, validated, timestamp, validationTime, vehicle

DTOs
  VehicleDTO
  TicketDTO

Tech Stack

  Java 17
  
  Jakarta EE (JAX-RS, JPA, EJB)
  
  PostgreSQL
  
  WildFly (Docker)
  
  Docker Compose

Development Notes
  
  JPA persistence unit (ticketingPU) is configured for automatic schema generation on startup.
  
  Exception handling includes custom exceptions for not found and duplicates.
  
  Vehicle registration numbers must be unique.
  
  UUIDs used for ticket codes.

For local setup standalone.xml and postgresql should be configured !
