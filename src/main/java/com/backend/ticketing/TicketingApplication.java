package com.backend.ticketing;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@ApplicationPath("/api")
@OpenAPIDefinition(
        info = @Info(title = "Ticketing API", version = "1.0", description = "Backend Ticketing Service for Unicard")
)
public class TicketingApplication extends Application {
}