package com.champsoft.propertyrentalplatform.rental.domain.model;

import com.champsoft.propertyrentalplatform.rental.domain.exception.InvalidRentException;

public final class Rent {
    private final double amount;

    public Rent(double amount) {
        if (amount == 0) throw new InvalidRentException("Rent is required");
        if (amount < 0) throw new InvalidRentException("Rent cannot be negative ");

        this.amount = amount;
    }

    public double amount() { return amount; }
}
