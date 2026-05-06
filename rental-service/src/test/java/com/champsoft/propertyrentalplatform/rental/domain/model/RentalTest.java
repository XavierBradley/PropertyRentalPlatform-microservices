package com.champsoft.propertyrentalplatform.rental.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Domain test → aggregate behavior
class RentalTest {

    private Rental validRental() {
        return new Rental(
                RentalId.newId(),
                new PropertyRef(UUID.randomUUID()),
                new OwnerRef(UUID.randomUUID()),
                new TenantRef(UUID.randomUUID()),
                new Rent(1500.0),
                new ExpiryDate(LocalDate.now().plusDays(30))
        );
    }

    @Test
    void shouldCreateActiveRentalByDefault() {

        // ------------------- Arrange -------------------
        Rental rental = validRental();

        // ------------------- Assert -------------------
        assertThat(rental.status()).isEqualTo(RentalStatus.ACTIVE);
        assertThat(rental.rentValue()).isEqualTo(1500.0);
    }

    @Test
    void shouldRenewActiveRental() {

        // ------------------- Arrange -------------------
        Rental rental = validRental();

        // ------------------- Act -------------------
        ExpiryDate newExpiry = new ExpiryDate(LocalDate.now().plusDays(60));
        rental.renew(newExpiry);

        // ------------------- Assert -------------------
        assertThat(rental.expiry().value()).isEqualTo(newExpiry.value());
    }

    @Test
    void shouldNotRenewInactiveRental() {

        // ------------------- Arrange -------------------
        Rental rental = validRental();
        rental.cancel();

        // ------------------- Assert -------------------
        assertThatThrownBy(() ->
                rental.renew(new ExpiryDate(LocalDate.now().plusDays(10)))
        ).isInstanceOf(RuntimeException.class)
                .hasMessage("Rental not ACTIVE");
    }

    @Test
    void shouldCancelRental() {

        // ------------------- Arrange -------------------
        Rental rental = validRental();

        // ------------------- Act -------------------
        rental.cancel();

        // ------------------- Assert -------------------
        assertThat(rental.status()).isEqualTo(RentalStatus.EXPIRED);
    }
}