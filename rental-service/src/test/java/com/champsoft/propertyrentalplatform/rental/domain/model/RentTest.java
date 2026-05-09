package com.champsoft.propertyrentalplatform.rental.domain.model;

import com.champsoft.propertyrentalplatform.rental.domain.exception.InvalidRentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RentTest {

    @Test
    @DisplayName("Should create valid rent")
    void shouldCreateValidRent() {

        Rent rent = new Rent(1850.0);

        assertThat(rent.amount()).isEqualTo(1850.0);
    }

    @Test
    @DisplayName("Should throw when rent is zero")
    void shouldThrowWhenRentIsZero() {

        assertThrows(
                InvalidRentException.class,
                () -> new Rent(0)
        );
    }

    @Test
    @DisplayName("Should throw when rent is negative")
    void shouldThrowWhenRentIsNegative() {

        assertThrows(
                InvalidRentException.class,
                () -> new Rent(-100)
        );
    }
}