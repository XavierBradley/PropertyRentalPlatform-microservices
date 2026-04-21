package com.champsoft.propertyrentalplatform.property.api.dto;

import jakarta.validation.constraints.*;

public record CreatePropertyRequest(
        double tax,
        @NotBlank String address) {}
