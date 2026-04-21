package com.champsoft.propertyrentalplatform.property.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class PropertyId {
    private final String value;

    private PropertyId(String value) {
        this.value = value;
    }

    public static PropertyId newId() {
        return new PropertyId(UUID.randomUUID().toString());
    }

    public static PropertyId of(String value) {
        return new PropertyId(value);
    }

    public String value() { return value; }

    @Override public boolean equals(Object o) {
        return (o instanceof PropertyId other) && Objects.equals(value, other.value);
    }
    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return value; }
}
