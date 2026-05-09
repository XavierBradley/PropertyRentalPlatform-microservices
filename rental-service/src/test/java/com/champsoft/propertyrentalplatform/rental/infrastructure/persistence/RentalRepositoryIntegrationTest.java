package com.champsoft.propertyrentalplatform.rental.infrastructure.persistence;

import com.champsoft.propertyrentalplatform.rental.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaRentalRepositoryAdapter.class)
class RentalRepositoryIntegrationTest {

    @Autowired
    private JpaRentalRepositoryAdapter adapter;

    @Autowired
    private SpringDataRentalRepository jpa;

    private Rental rental(UUID id) {

        return new Rental(
                RentalId.of(id),
                new PropertyRef(UUID.randomUUID()),
                new OwnerRef(UUID.randomUUID()),
                new TenantRef(UUID.randomUUID()),
                new Rent(1850.0),
                new ExpiryDate(LocalDate.now().plusMonths(6))
        );
    }

    @Test
    @DisplayName("should save rental")
    void shouldSaveRental() {

        UUID id = UUID.randomUUID();

        Rental rental = rental(id);

        adapter.save(rental);

        Optional<RentalJpaEntity> saved = jpa.findById(id);

        assertThat(saved).isPresent();

        assertThat(saved.get().id)
                .isEqualTo(id);

        assertThat(saved.get().status)
                .isEqualTo("ACTIVE");

        assertThat(saved.get().rent.doubleValue())
                .isEqualTo(1850.0);
    }

    @Test
    @DisplayName("should find rental by id")
    void shouldFindRentalById() {

        UUID id = UUID.randomUUID();

        Rental rental = rental(id);

        adapter.save(rental);

        Optional<Rental> found =
                adapter.findById(RentalId.of(id));

        assertThat(found).isPresent();

        assertThat(found.get().id().value())
                .isEqualTo(id);

        assertThat(found.get().status())
                .isEqualTo(RentalStatus.ACTIVE);
    }

    @Test
    @DisplayName("should return empty when rental does not exist")
    void shouldReturnEmptyWhenRentalDoesNotExist() {

        Optional<Rental> result =
                adapter.findById(RentalId.of(UUID.randomUUID()));

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("should return all rentals")
    void shouldReturnAllRentals() {

        Rental rental1 = rental(UUID.randomUUID());

        Rental rental2 = rental(UUID.randomUUID());

        adapter.save(rental1);
        adapter.save(rental2);

        List<Rental> rentals = adapter.findAll();

        assertThat(rentals).hasSize(2);
    }

    @Test
    @DisplayName("should delete rental")
    void shouldDeleteRental() {

        UUID id = UUID.randomUUID();

        Rental rental = rental(id);

        adapter.save(rental);

        adapter.deleteById(RentalId.of(id));

        Optional<RentalJpaEntity> deleted =
                jpa.findById(id);

        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("should restore cancelled rental from database")
    void shouldRestoreCancelledRentalFromDatabase() {

        UUID id = UUID.randomUUID();

        Rental rental = rental(id);

        rental.cancel();

        adapter.save(rental);

        Optional<Rental> restored =
                adapter.findById(RentalId.of(id));

        assertThat(restored).isPresent();

        assertThat(restored.get().status())
                .isEqualTo(RentalStatus.EXPIRED);
    }
}