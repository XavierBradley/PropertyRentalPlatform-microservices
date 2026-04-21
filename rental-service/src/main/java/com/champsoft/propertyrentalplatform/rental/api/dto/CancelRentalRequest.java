package com.champsoft.propertyrentalplatform.rental.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CancelRentalRequest(
        @NotBlank String id
) {

    public String rentalId() {
        return id;
    }
}
