package com.champsoft.propertyrentalplatform.property.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class PropertyId {
    private final UUID value;

    private PropertyId(UUID value) {
        this.value = value;
    }

    public static PropertyId newId() {
        return new PropertyId(UUID.randomUUID());
    }

    public static PropertyId of(UUID value) {
        return new PropertyId(value);
    }

    public UUID value() { return value; }

    @Override public boolean equals(Object o) {
        return (o instanceof PropertyId other) && Objects.equals(value, other.value);
    }
    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return String.valueOf(value); }
}
