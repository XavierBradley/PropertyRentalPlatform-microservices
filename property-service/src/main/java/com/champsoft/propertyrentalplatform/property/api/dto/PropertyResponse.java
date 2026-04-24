package com.champsoft.propertyrentalplatform.property.api.dto;

import java.util.UUID;

public record PropertyResponse(
        UUID id,
        double tax,
        String address,
        String status) {}
