package com.champsoft.propertyrentalplatform.property.domain.model;

import com.champsoft.propertyrentalplatform.property.domain.exception.PropertyAlreadyBeingRentedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyTest {

    private Property property() {

        return new Property(
                PropertyId.newId(),
                new PropertyTax(0.01),
                new Address("123 Main Street")
        );
    }

    @Test
    @DisplayName("Should create available property")
    void shouldCreateAvailableProperty() {

        Property property = property();

        assertThat(property.status())
                .isEqualTo(PropertyStatus.AVAILABLE);

        assertThat(property.tax().value())
                .isEqualTo(0.01);

        assertThat(property.address().value())
                .isEqualTo("123 Main Street");
    }

    @Test
    @DisplayName("Should update property")
    void shouldUpdateProperty() {

        Property property = property();

        property.update(
                new PropertyTax(0.02),
                new Address("456 Park Avenue")
        );

        assertThat(property.tax().value())
                .isEqualTo(0.02);

        assertThat(property.address().value())
                .isEqualTo("456 Park Avenue");
    }

    @Test
    @DisplayName("Should rent property")
    void shouldRentProperty() {

        Property property = property();

        property.rent();

        assertThat(property.status())
                .isEqualTo(PropertyStatus.UNAVAILABLE);
    }

    @Test
    @DisplayName("Should throw when property already rented")
    void shouldThrowWhenPropertyAlreadyRented() {

        Property property = property();

        property.rent();

        assertThrows(
                PropertyAlreadyBeingRentedException.class,
                property::rent
        );
    }

    @Test
    @DisplayName("Should be eligible when available")
    void shouldBeEligibleWhenAvailable() {

        Property property = property();

        assertThat(property.isEligibleToBeRented())
                .isTrue();
    }

    @Test
    @DisplayName("Should not be eligible when unavailable")
    void shouldNotBeEligibleWhenUnavailable() {

        Property property = property();

        property.rent();

        assertThat(property.isEligibleToBeRented())
                .isFalse();
    }
}