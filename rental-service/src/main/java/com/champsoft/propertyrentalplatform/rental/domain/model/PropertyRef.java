package com.champsoft.propertyrentalplatform.rental.domain.model;

import java.util.UUID;

public record PropertyRef(UUID value) {
    public PropertyRef {
        if (value == null) throw new IllegalArgumentException("propertyId is required");
    }
}
