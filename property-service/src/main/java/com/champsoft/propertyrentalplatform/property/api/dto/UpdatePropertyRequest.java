package com.champsoft.propertyrentalplatform.property.api.dto;

import jakarta.validation.constraints.*;

public record UpdatePropertyRequest(
        double tax,
        @NotBlank String address) {}
