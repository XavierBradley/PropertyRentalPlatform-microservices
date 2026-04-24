package com.champsoft.propertyrentalplatform.rental.domain.model;

import java.util.UUID;

public final class RentalId {
    private final UUID value;

    private RentalId(UUID value) { this.value = value; }

    public static RentalId newId() { return new RentalId(UUID.randomUUID()); }
    public static RentalId of(UUID value) { return new RentalId(value); }
    public UUID value() { return value; }
}
