package com.champsoft.propertyrentalplatform.rental.domain.model;

public record PropertyRef(String value) {
    public PropertyRef {
        if (value == null) throw new IllegalArgumentException("propertyId is required");
        value = value.trim();
        if (value.isEmpty()) throw new IllegalArgumentException("propertyId is required");
    }
}
