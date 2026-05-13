package com.champsoft.propertyrentalplatform.rental.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Future;

import java.time.LocalDate;
import java.util.UUID;

public record RegisterPropertyRequest(

        @NotNull UUID propertyId,

        @NotNull UUID ownerId,

        @NotNull UUID tenantId,

        @Positive(message = "rent must be greater than zero")
        double rent,

        @NotNull
        @Future(message = "expiry must be in the future")
        LocalDate expiry

) {}