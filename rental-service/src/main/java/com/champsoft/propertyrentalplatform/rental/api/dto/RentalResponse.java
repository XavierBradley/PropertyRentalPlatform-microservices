package com.champsoft.propertyrentalplatform.rental.api.dto;

import java.time.LocalDate;

public record RentalResponse(
        String id,
        String propertyId,
        String ownerId,
        String tenantId,
        double rent,
        LocalDate expiry,
        String status) {}
