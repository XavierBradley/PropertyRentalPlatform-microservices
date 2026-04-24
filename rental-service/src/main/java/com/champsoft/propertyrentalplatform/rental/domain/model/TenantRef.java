package com.champsoft.propertyrentalplatform.rental.domain.model;

import java.util.UUID;

public record TenantRef(UUID value) {
    public TenantRef {
        if (value == null) throw new IllegalArgumentException("tenantId is required");
    }
}
