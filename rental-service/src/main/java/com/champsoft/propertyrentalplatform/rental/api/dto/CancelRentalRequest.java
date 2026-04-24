package com.champsoft.propertyrentalplatform.rental.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CancelRentalRequest(
        @NotNull UUID id
) {

    public UUID rentalId() {
        return id;
    }
}
