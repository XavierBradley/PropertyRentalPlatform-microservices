package com.champsoft.propertyrentalplatform.property.api.dto;

public record PropertyResponse(
        String id,
        double tax,
        String address,
        String status) {}
