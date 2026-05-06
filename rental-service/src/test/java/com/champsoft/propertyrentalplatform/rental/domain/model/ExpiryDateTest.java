package com.champsoft.propertyrentalplatform.rental.domain.model;

import com.champsoft.propertyrentalplatform.rental.domain.exception.ExpiryDateMustBeFutureException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Domain test → time-based validation rules
class ExpiryDateTest {

    @Test
    void shouldCreateValidFutureExpiryDate() {

        // ------------------- Act -------------------
        ExpiryDate expiry = new ExpiryDate(LocalDate.now().plusDays(10));

        // ------------------- Assert -------------------
        assertThat(expiry.value()).isAfter(LocalDate.now());
    }

    @Test
    void shouldRejectNullExpiryDate() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new ExpiryDate(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectPastExpiryDate() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new ExpiryDate(LocalDate.now().minusDays(1)))
                .isInstanceOf(ExpiryDateMustBeFutureException.class);
    }

    @Test
    void shouldRejectTodayAsExpiryDate() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new ExpiryDate(LocalDate.now()))
                .isInstanceOf(ExpiryDateMustBeFutureException.class);
    }
}