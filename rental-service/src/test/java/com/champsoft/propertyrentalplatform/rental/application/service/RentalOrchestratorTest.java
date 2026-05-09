package com.champsoft.propertyrentalplatform.rental.application.service;

import com.champsoft.propertyrentalplatform.rental.application.exception.CrossContextValidationException;
import com.champsoft.propertyrentalplatform.rental.application.port.out.*;
import com.champsoft.propertyrentalplatform.rental.domain.model.Rental;
import com.champsoft.propertyrentalplatform.rental.domain.model.RentalStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalOrchestratorTest {

    @Mock
    private PropertyEligibilityPort propertyPort;

    @Mock
    private OwnerEligibilityPort ownerPort;

    @Mock
    private TenantEligibilityPort tenantPort;

    @Mock
    private RentalRepositoryPort repo;

    @InjectMocks
    private RentalOrchestrator service;

    @Nested
    @DisplayName("Register rentals")
    class RegisterRentalTests {

        @Test
        void shouldRegisterRentalSuccessfully() {

            UUID propertyId = UUID.randomUUID();
            UUID ownerId = UUID.randomUUID();
            UUID tenantId = UUID.randomUUID();

            when(propertyPort.isEligible(propertyId)).thenReturn(true);
            when(ownerPort.isEligible(ownerId)).thenReturn(true);
            when(tenantPort.isEligible(tenantId)).thenReturn(true);

            when(repo.save(any(Rental.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Rental rental = service.register(
                    propertyId,
                    ownerId,
                    tenantId,
                    1850.0,
                    LocalDate.now().plusMonths(6)
            );

            assertThat(rental).isNotNull();

            assertThat(rental.status())
                    .isEqualTo(RentalStatus.ACTIVE);

            verify(repo).save(any(Rental.class));
        }

        @Test
        void shouldThrowWhenPropertyNotEligible() {

            UUID propertyId = UUID.randomUUID();
            UUID ownerId = UUID.randomUUID();
            UUID tenantId = UUID.randomUUID();

            when(propertyPort.isEligible(propertyId))
                    .thenReturn(false);

            assertThrows(
                    CrossContextValidationException.class,
                    () -> service.register(
                            propertyId,
                            ownerId,
                            tenantId,
                            1850.0,
                            LocalDate.now().plusMonths(6)
                    )
            );

            verify(repo, never()).save(any());
        }

        @Test
        void shouldThrowWhenOwnerNotEligible() {

            UUID propertyId = UUID.randomUUID();
            UUID ownerId = UUID.randomUUID();
            UUID tenantId = UUID.randomUUID();

            when(propertyPort.isEligible(propertyId))
                    .thenReturn(true);

            when(ownerPort.isEligible(ownerId))
                    .thenReturn(false);

            assertThrows(
                    CrossContextValidationException.class,
                    () -> service.register(
                            propertyId,
                            ownerId,
                            tenantId,
                            1850.0,
                            LocalDate.now().plusMonths(6)
                    )
            );

            verify(repo, never()).save(any());
        }

        @Test
        void shouldThrowWhenTenantNotEligible() {

            UUID propertyId = UUID.randomUUID();
            UUID ownerId = UUID.randomUUID();
            UUID tenantId = UUID.randomUUID();

            when(propertyPort.isEligible(propertyId))
                    .thenReturn(true);

            when(ownerPort.isEligible(ownerId))
                    .thenReturn(true);

            when(tenantPort.isEligible(tenantId))
                    .thenReturn(false);

            assertThrows(
                    CrossContextValidationException.class,
                    () -> service.register(
                            propertyId,
                            ownerId,
                            tenantId,
                            1850.0,
                            LocalDate.now().plusMonths(6)
                    )
            );

            verify(repo, never()).save(any());
        }
    }
}