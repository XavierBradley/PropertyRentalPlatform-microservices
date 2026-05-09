package com.champsoft.propertyrentalplatform.owners.application.service;

import com.champsoft.propertyrentalplatform.owners.application.exception.OwnerNotFoundException;
import com.champsoft.propertyrentalplatform.owners.application.port.out.OwnerRepositoryPort;
import com.champsoft.propertyrentalplatform.owners.domain.model.Address;
import com.champsoft.propertyrentalplatform.owners.domain.model.FullName;
import com.champsoft.propertyrentalplatform.owners.domain.model.Owner;
import com.champsoft.propertyrentalplatform.owners.domain.model.OwnerId;
import com.champsoft.propertyrentalplatform.owners.domain.model.OwnerStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerEligibilityServiceTest {

    @Mock
    private OwnerRepositoryPort repo;

    @InjectMocks
    private OwnerEligibilityService service;

    private Owner owner(UUID id, OwnerStatus status) {

        Owner owner = new Owner(
                OwnerId.of(id),
                new FullName("John Smith"),
                new Address("Montreal")
        );

        if (status == OwnerStatus.INACTIVE) {
            owner.deactivate();
        }

        return owner;
    }

    @Nested
    @DisplayName("Eligibility checks")
    class EligibilityTests {

        @Test
        void shouldReturnTrueWhenOwnerIsActive() {

            UUID id = UUID.randomUUID();

            Owner owner = owner(id, OwnerStatus.ACTIVE);

            when(repo.findById(OwnerId.of(id)))
                    .thenReturn(Optional.of(owner));

            boolean eligible = service.isEligible(id);

            assertThat(eligible).isTrue();
        }

        @Test
        void shouldReturnFalseWhenOwnerIsInactive() {

            UUID id = UUID.randomUUID();

            Owner owner = owner(id, OwnerStatus.INACTIVE);

            when(repo.findById(OwnerId.of(id)))
                    .thenReturn(Optional.of(owner));

            boolean eligible = service.isEligible(id);

            assertThat(eligible).isFalse();
        }

        @Test
        void shouldThrowOwnerNotFoundException() {

            UUID id = UUID.randomUUID();

            when(repo.findById(OwnerId.of(id)))
                    .thenReturn(Optional.empty());

            assertThrows(
                    OwnerNotFoundException.class,
                    () -> service.isEligible(id)
            );
        }
    }
}