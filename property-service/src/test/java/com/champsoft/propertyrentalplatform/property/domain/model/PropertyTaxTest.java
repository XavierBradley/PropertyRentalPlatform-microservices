package com.champsoft.propertyrentalplatform.property.domain.model;

import com.champsoft.propertyrentalplatform.property.domain.exception.InvalidPropertyTaxException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyTaxTest {

    @Test
    @DisplayName("Should create valid property tax")
    void shouldCreateValidPropertyTax() {

        PropertyTax tax = new PropertyTax(0.01);

        assertThat(tax.value()).isEqualTo(0.01);
    }

    @Test
    @DisplayName("Should throw when tax is zero")
    void shouldThrowWhenTaxIsZero() {

        assertThrows(
                InvalidPropertyTaxException.class,
                () -> new PropertyTax(0)
        );
    }

    @Test
    @DisplayName("Should throw when tax is negative")
    void shouldThrowWhenTaxIsNegative() {

        assertThrows(
                InvalidPropertyTaxException.class,
                () -> new PropertyTax(-0.01)
        );
    }

    @Test
    @DisplayName("Should throw when tax is below minimum")
    void shouldThrowWhenTaxIsBelowMinimum() {

        assertThrows(
                InvalidPropertyTaxException.class,
                () -> new PropertyTax(0.002)
        );
    }

    @Test
    @DisplayName("Should throw when tax exceeds maximum")
    void shouldThrowWhenTaxExceedsMaximum() {

        assertThrows(
                InvalidPropertyTaxException.class,
                () -> new PropertyTax(0.03)
        );
    }
}