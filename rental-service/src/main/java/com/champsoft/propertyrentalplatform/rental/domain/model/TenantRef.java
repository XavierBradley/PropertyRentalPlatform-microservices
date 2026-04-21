package com.champsoft.propertyrentalplatform.rental.domain.model;

public record TenantRef(String value) {
    public TenantRef {
        if (value == null) throw new IllegalArgumentException("tenantId is required");
        value = value.trim();
        if (value.isEmpty()) throw new IllegalArgumentException("tenantId is required");
    }
}
