package com.champsoft.propertyrentalplatform.rental.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CancelRentalRequest(
        @NotBlank UUID id
) {

    public UUID rentalId() {
        return id;
    }
}
