package com.champsoft.propertyrentalplatform.tenant.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class TenantId {
    private final UUID value;

    private TenantId(UUID value) { this.value = value; }

    public static TenantId newId() { return new TenantId(UUID.randomUUID()); }
    public static TenantId of(UUID value) { return new TenantId(value); }
    public UUID value() { return value; }

    @Override public boolean equals(Object o) {
        return (o instanceof TenantId other) && Objects.equals(value, other.value);
    }
    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return String.valueOf(value); }
}
