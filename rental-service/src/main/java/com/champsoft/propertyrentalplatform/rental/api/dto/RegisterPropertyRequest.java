package com.champsoft.propertyrentalplatform.rental.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterPropertyRequest(
        @NotBlank String propertyId,
        @NotBlank String ownerId,
        @NotBlank String tenantId,
        double rent,
        @NotNull LocalDate expiry) {}
