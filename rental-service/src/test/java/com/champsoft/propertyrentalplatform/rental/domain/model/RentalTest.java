package com.champsoft.propertyrentalplatform.rental.domain.model;

import com.champsoft.propertyrentalplatform.rental.domain.exception.RentalNotActiveException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RentalTest {

    private Rental rental() {

        return new Rental(
                RentalId.newId(),
                new PropertyRef(UUID.randomUUID()),
                new OwnerRef(UUID.randomUUID()),
                new TenantRef(UUID.randomUUID()),
                new Rent(1850.0),
                new ExpiryDate(LocalDate.now().plusMonths(6))
        );
    }

    @Test
    @DisplayName("Should create active rental")
    void shouldCreateActiveRental() {

        Rental rental = rental();

        assertThat(rental.status())
                .isEqualTo(RentalStatus.ACTIVE);

        assertThat(rental.rent().amount())
                .isEqualTo(1850.0);
    }

    @Test
    @DisplayName("Should renew active rental")
    void shouldRenewActiveRental() {

        Rental rental = rental();

        LocalDate newExpiry = LocalDate.now().plusYears(1);

        rental.renew(new ExpiryDate(newExpiry));

        assertThat(rental.expiry().value())
                .isEqualTo(newExpiry);
    }

    @Test
    @DisplayName("Should cancel rental")
    void shouldCancelRental() {

        Rental rental = rental();

        rental.cancel();

        assertThat(rental.status())
                .isEqualTo(RentalStatus.EXPIRED);
    }

    @Test
    @DisplayName("Should throw when renewing expired rental")
    void shouldThrowWhenRenewingExpiredRental() {

        Rental rental = rental();

        rental.cancel();

        assertThrows(
                RentalNotActiveException.class,
                () -> rental.renew(
                        new ExpiryDate(LocalDate.now().plusYears(1))
                )
        );
    }

    @Test
    @DisplayName("Should throw when cancelling expired rental")
    void shouldThrowWhenCancellingExpiredRental() {

        Rental rental = rental();

        rental.cancel();

        assertThrows(
                RentalNotActiveException.class,
                rental::cancel
        );
    }
}