package com.champsoft.propertyrentalplatform.rental.domain.model;

import com.champsoft.propertyrentalplatform.rental.domain.exception.InvalidRentException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Domain test → business rules
class RentTest {

    @Test
    void shouldCreateValidRent() {

        // ------------------- Act -------------------
        Rent rent = new Rent(1500.0);

        // ------------------- Assert -------------------
        assertThat(rent.amount()).isEqualTo(1500.0);
    }

    @Test
    void shouldRejectZeroRent() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new Rent(0))
                .isInstanceOf(InvalidRentException.class);
    }

    @Test
    void shouldRejectNegativeRent() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new Rent(-100))
                .isInstanceOf(InvalidRentException.class);
    }
}