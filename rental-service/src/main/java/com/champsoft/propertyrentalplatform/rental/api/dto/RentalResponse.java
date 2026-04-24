package com.champsoft.propertyrentalplatform.rental.api.dto;

import java.time.LocalDate;
import java.util.UUID;

public record RentalResponse(
        UUID id,
        UUID propertyId,
        UUID ownerId,
        UUID tenantId,
        double rent,
        LocalDate expiry,
        String status) {}
