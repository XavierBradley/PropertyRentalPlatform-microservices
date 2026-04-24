package com.champsoft.propertyrentalplatform.rental.domain.model;

import java.util.UUID;

public record OwnerRef(UUID value) {
    public OwnerRef {
        if (value == null) throw new IllegalArgumentException("ownerId is required");
    }
}
