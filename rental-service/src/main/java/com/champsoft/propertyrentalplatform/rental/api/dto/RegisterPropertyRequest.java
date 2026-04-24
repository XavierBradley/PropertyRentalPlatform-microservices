package com.champsoft.propertyrentalplatform.rental.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record RegisterPropertyRequest(
        @NotBlank UUID propertyId,
        @NotBlank UUID ownerId,
        @NotBlank UUID tenantId,
        double rent,
        @NotNull LocalDate expiry) {}
