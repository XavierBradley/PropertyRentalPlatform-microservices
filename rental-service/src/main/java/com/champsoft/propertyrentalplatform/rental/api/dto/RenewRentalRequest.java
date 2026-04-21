package com.champsoft.propertyrentalplatform.rental.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RenewRentalRequest(@NotNull LocalDate newExpiry) {}
