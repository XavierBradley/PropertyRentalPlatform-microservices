package com.champsoft.propertyrentalplatform.property.domain.model;

import com.champsoft.propertyrentalplatform.property.domain.exception.InvalidAddressException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Domain test → validation rules only
class AddressTest {

    @Test
    void shouldCreateValidAddress() {

        // ------------------- Act -------------------
        Address address = new Address("123 Maple St, Montreal");

        // ------------------- Assert -------------------
        assertThat(address.value()).isEqualTo("123 Maple St, Montreal");
    }

    @Test
    void shouldTrimAddressValue() {

        // ------------------- Act -------------------
        Address address = new Address("   123 Maple St, Montreal   ");

        // ------------------- Assert -------------------
        assertThat(address.value()).isEqualTo("123 Maple St, Montreal");
    }

    @Test
    void shouldRejectNullAddress() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new Address(null))
                .isInstanceOf(InvalidAddressException.class);
    }

    @Test
    void shouldRejectTooLongAddress() {

        // ------------------- Arrange -------------------
        String longAddress = "A".repeat(201);

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new Address(longAddress))
                .isInstanceOf(InvalidAddressException.class);
    }
}