package com.champsoft.propertyrentalplatform.owners.domain.model;

import com.champsoft.propertyrentalplatform.owners.domain.exception.InvalidAddressException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressTest {

    @Test
    void shouldCreateValidAddress() {

        Address address = new Address("Montreal");

        assertThat(address.value()).isEqualTo("Montreal");
    }

    @Test
    void shouldTrimAddress() {

        Address address = new Address("  Montreal  ");

        assertThat(address.value()).isEqualTo("Montreal");
    }

    @Test
    void shouldAllowNullAddress() {

        Address address = new Address(null);


        assertThat(address.value()).isNull();
    }

    @Test
    void shouldConvertBlankAddressToNull() {

        Address address = new Address("   ");

        assertThat(address.value()).isNull();
    }

    @Test
    void shouldThrowExceptionWhenAddressIsTooLong() {


        String longAddress = "A".repeat(201);

        assertThrows(InvalidAddressException.class, () -> new Address(longAddress));
    }
}