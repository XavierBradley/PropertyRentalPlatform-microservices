package com.champsoft.propertyrentalplatform.rental.domain.model;

import com.champsoft.propertyrentalplatform.rental.domain.exception.ExpiryDateMustBeFutureException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpiryDateTest {

    @Test
    @DisplayName("Should create valid expiry date")
    void shouldCreateValidExpiryDate() {

        LocalDate future = LocalDate.now().plusMonths(6);

        ExpiryDate expiry = new ExpiryDate(future);

        assertThat(expiry.value()).isEqualTo(future);
    }

    @Test
    @DisplayName("Should throw when expiry is null")
    void shouldThrowWhenExpiryIsNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new ExpiryDate(null)
        );
    }

    @Test
    @DisplayName("Should throw when expiry is today")
    void shouldThrowWhenExpiryIsToday() {

        assertThrows(
                ExpiryDateMustBeFutureException.class,
                () -> new ExpiryDate(LocalDate.now())
        );
    }

    @Test
    @DisplayName("Should throw when expiry is in the past")
    void shouldThrowWhenExpiryIsPast() {

        assertThrows(
                ExpiryDateMustBeFutureException.class,
                () -> new ExpiryDate(LocalDate.now().minusDays(1))
        );
    }
}