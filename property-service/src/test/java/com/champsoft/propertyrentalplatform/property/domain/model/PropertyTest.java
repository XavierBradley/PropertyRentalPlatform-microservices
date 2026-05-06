package com.champsoft.propertyrentalplatform.property.domain.model;

import com.champsoft.propertyrentalplatform.property.domain.exception.PropertyAlreadyBeingRentedException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Domain test → aggregate business rules
class PropertyTest {

    private Property validProperty() {
        return new Property(
                PropertyId.newId(),
                new PropertyTax(0.015),
                new Address("123 Maple St, Montreal")
        );
    }

    @Test
    void shouldCreateAvailablePropertyByDefault() {

        // ------------------- Arrange -------------------
        Property property = validProperty();

        // ------------------- Assert -------------------
        assertThat(property.status()).isEqualTo(PropertyStatus.AVAILABLE);
        assertThat(property.isEligibleToBeRented()).isTrue();
    }

    @Test
    void shouldUpdatePropertyDetails() {

        // ------------------- Arrange -------------------
        Property property = validProperty();

        // ------------------- Act -------------------
        property.update(
                new PropertyTax(0.02),
                new Address("456 Queen St, Toronto")
        );

        // ------------------- Assert -------------------
        assertThat(property.tax().value()).isEqualTo(0.02);
        assertThat(property.address().value()).isEqualTo("456 Queen St, Toronto");
    }

    @Test
    void shouldMarkPropertyAsRented() {

        // ------------------- Arrange -------------------
        Property property = validProperty();

        // ------------------- Act -------------------
        property.rent();

        // ------------------- Assert -------------------
        assertThat(property.status()).isEqualTo(PropertyStatus.UNAVAILABLE);
        assertThat(property.isEligibleToBeRented()).isFalse();
    }

    @Test
    void shouldNotAllowRentingAlreadyUnavailableProperty() {

        // ------------------- Arrange -------------------
        Property property = validProperty();
        property.rent();

        // ------------------- Assert -------------------
        assertThatThrownBy(property::rent)
                .isInstanceOf(PropertyAlreadyBeingRentedException.class);
    }
}