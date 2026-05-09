package com.champsoft.propertyrentalplatform.property.domain.model;

import com.champsoft.propertyrentalplatform.property.domain.exception.InvalidAddressException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressTest {

    @Test
    @DisplayName("Should create valid address")
    void shouldCreateValidAddress() {

        Address address = new Address("123 Main Street");

        assertThat(address.value())
                .isEqualTo("123 Main Street");
    }

    @Test
    @DisplayName("Should trim address")
    void shouldTrimAddress() {

        Address address = new Address("  123 Main Street  ");

        assertThat(address.value())
                .isEqualTo("123 Main Street");
    }

    @Test
    @DisplayName("Should throw when address is null")
    void shouldThrowWhenAddressIsNull() {

        assertThrows(
                InvalidAddressException.class,
                () -> new Address(null)
        );
    }

    @Test
    @DisplayName("Should convert blank address to null")
    void shouldConvertBlankAddressToNull() {

        Address address = new Address("   ");

        assertThat(address.value()).isNull();
    }

    @Test
    @DisplayName("Should throw when address exceeds max length")
    void shouldThrowWhenAddressExceedsMaxLength() {

        String longAddress = "A".repeat(201);

        assertThrows(
                InvalidAddressException.class,
                () -> new Address(longAddress)
        );
    }
}