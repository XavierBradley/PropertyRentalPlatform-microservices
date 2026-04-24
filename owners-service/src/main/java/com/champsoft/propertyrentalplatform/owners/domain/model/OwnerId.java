package com.champsoft.propertyrentalplatform.owners.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class OwnerId {
    private final UUID value;

    private OwnerId(UUID value) { this.value = value; }

    public static OwnerId newId() { return new OwnerId(UUID.randomUUID()); }
    public static OwnerId of(UUID value) { return new OwnerId(value); }
    public UUID value() { return value; }

    @Override public boolean equals(Object o) {
        return (o instanceof OwnerId other) && Objects.equals(value, other.value);
    }
    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return String.valueOf(value); }
}
