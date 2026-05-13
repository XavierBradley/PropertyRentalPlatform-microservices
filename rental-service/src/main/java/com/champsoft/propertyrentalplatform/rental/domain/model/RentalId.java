package com.champsoft.propertyrentalplatform.rental.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class RentalId {

    private final UUID value;

    private RentalId(UUID value) {

        this.value = Objects.requireNonNull(
                value,
                "Rental ID cannot be null"
        );
    }

    public static RentalId newId() {
        return new RentalId(UUID.randomUUID());
    }

    public static RentalId of(UUID value) {
        return new RentalId(value);
    }

    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RentalId rentalId = (RentalId) o;

        return value.equals(rentalId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}