package com.champsoft.propertyrentalplatform.owners.domain.model;

import com.champsoft.propertyrentalplatform.owners.domain.exception.InvalidOwnerNameException;
import java.util.Objects;

public final class FullName {
    private final String value;

    public FullName(String value) {
        if (value == null) throw new InvalidOwnerNameException("full name is required");
        String v = value.trim();
        if (v.length() < 2 || v.length() > 100) throw new InvalidOwnerNameException("full name length must be 2..100");
        this.value = v;
    }

    public String value() { return value; }

    @Override public boolean equals(Object o) {
        return (o instanceof FullName other) && Objects.equals(value, other.value);
    }
    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return value; }
}
