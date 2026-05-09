package com.champsoft.propertyrentalplatform.rental.application.service;

import com.champsoft.propertyrentalplatform.rental.application.exception.RentalNotFoundException;
import com.champsoft.propertyrentalplatform.rental.application.port.out.RentalRepositoryPort;
import com.champsoft.propertyrentalplatform.rental.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalCrudServiceTest {

    @Mock
    private RentalRepositoryPort repo;

    @InjectMocks
    private RentalCrudService service;

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

    @Nested
    @DisplayName("Get rentals")
    class GetRentalTests {

        @Test
        void shouldReturnRentalById() {

            UUID id = UUID.randomUUID();

            Rental rental = rental(id);

            when(repo.findById(RentalId.of(id)))
                    .thenReturn(Optional.of(rental));

            Rental found = service.get(id);

            assertThat(found).isEqualTo(rental);

            verify(repo).findById(RentalId.of(id));
        }

        @Test
        void shouldThrowRentalNotFoundException() {

            UUID id = UUID.randomUUID();

            when(repo.findById(RentalId.of(id)))
                    .thenReturn(Optional.empty());

            assertThrows(
                    RentalNotFoundException.class,
                    () -> service.get(id)
            );
        }

        @Test
        void shouldReturnAllRentals() {

            List<Rental> rentals = List.of(
                    rental(UUID.randomUUID()),
                    rental(UUID.randomUUID())
            );

            when(repo.findAll()).thenReturn(rentals);

            List<Rental> result = service.list();

            assertThat(result).hasSize(2);

            verify(repo).findAll();
        }
    }

    @Nested
    @DisplayName("Renew rentals")
    class RenewRentalTests {

        @Test
        void shouldRenewRentalSuccessfully() {

            UUID id = UUID.randomUUID();

            Rental rental = rental(id);

            when(repo.findById(RentalId.of(id)))
                    .thenReturn(Optional.of(rental));

            when(repo.save(any(Rental.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            LocalDate newExpiry = LocalDate.now().plusYears(1);

            Rental renewed = service.renew(id, newExpiry);

            assertThat(renewed.expiry().value())
                    .isEqualTo(newExpiry);

            verify(repo).save(rental);
        }

        @Test
        void shouldThrowWhenRenewingMissingRental() {

            UUID id = UUID.randomUUID();

            when(repo.findById(RentalId.of(id)))
                    .thenReturn(Optional.empty());

            assertThrows(
                    RentalNotFoundException.class,
                    () -> service.renew(id, LocalDate.now().plusMonths(6))
            );

            verify(repo, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Cancel rentals")
    class CancelRentalTests {

        @Test
        void shouldCancelRentalSuccessfully() {

            UUID id = UUID.randomUUID();

            Rental rental = rental(id);

            when(repo.findById(RentalId.of(id)))
                    .thenReturn(Optional.of(rental));

            when(repo.save(any(Rental.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Rental cancelled = service.cancel(id);

            assertThat(cancelled.status())
                    .isEqualTo(RentalStatus.EXPIRED);

            verify(repo).save(rental);
        }

        @Test
        void shouldThrowWhenCancellingMissingRental() {

            UUID id = UUID.randomUUID();

            when(repo.findById(RentalId.of(id)))
                    .thenReturn(Optional.empty());

            assertThrows(
                    RentalNotFoundException.class,
                    () -> service.cancel(id)
            );

            verify(repo, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Delete rentals")
    class DeleteRentalTests {

        @Test
        void shouldDeleteRentalSuccessfully() {

            UUID id = UUID.randomUUID();

            Rental rental = rental(id);

            when(repo.findById(RentalId.of(id)))
                    .thenReturn(Optional.of(rental));

            service.delete(id);

            verify(repo).deleteById(RentalId.of(id));
        }

        @Test
        void shouldThrowWhenDeletingMissingRental() {

            UUID id = UUID.randomUUID();

            when(repo.findById(RentalId.of(id)))
                    .thenReturn(Optional.empty());

            assertThrows(
                    RentalNotFoundException.class,
                    () -> service.delete(id)
            );

            verify(repo, never()).deleteById(any());
        }
    }
}