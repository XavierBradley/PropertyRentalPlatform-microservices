package com.champsoft.propertyrentalplatform.rental.domain.model;

import java.util.UUID;

public final class RentalId {
    private final String value;

    private RentalId(String value) { this.value = value; }

    public static RentalId newId() { return new RentalId(UUID.randomUUID().toString()); }
    public static RentalId of(String value) { return new RentalId(value); }
    public String value() { return value; }
}
