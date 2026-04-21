package com.champsoft.propertyrentalplatform.tenant.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class TenantId {
    private final String value;

    private TenantId(String value) { this.value = value; }

    public static TenantId newId() { return new TenantId(UUID.randomUUID().toString()); }
    public static TenantId of(String value) { return new TenantId(value); }
    public String value() { return value; }

    @Override public boolean equals(Object o) {
        return (o instanceof TenantId other) && Objects.equals(value, other.value);
    }
    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return value; }
}
