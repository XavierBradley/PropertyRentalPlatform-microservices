package com.champsoft.propertyrentalplatform.owners.application.service;

import com.champsoft.propertyrentalplatform.owners.application.exception.DuplicateOwnerException;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerCrudServiceTest {

    @Mock
    private OwnerRepositoryPort repo;

    @InjectMocks
    private OwnerCrudService service;

    private Owner sampleOwner(UUID id, String fullName, String address) {
        return new Owner(
                OwnerId.of(id),
                new FullName(fullName),
                new Address(address)
        );
    }

    @Nested
    @DisplayName("Create owner")
    class CreateOwnerTests {

        @Test
        void shouldCreateOwnerSuccessfully() {

            when(repo.existsByFullName("John Smith")).thenReturn(false);
            when(repo.save(any(Owner.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Owner saved = service.create("John Smith", "Montreal");

            assertThat(saved).isNotNull();
            assertThat(saved.id()).isNotNull();
            assertThat(saved.fullName().value()).isEqualTo("John Smith");
            assertThat(saved.address().value()).isEqualTo("Montreal");

            // Actual implementation defaults to ACTIVE
            assertThat(saved.status()).isEqualTo(OwnerStatus.ACTIVE);

            verify(repo).existsByFullName("John Smith");
            verify(repo).save(any(Owner.class));
        }

        @Test
        void shouldThrowDuplicateOwnerException() {

            when(repo.existsByFullName("John Smith")).thenReturn(true);

            assertThrows(DuplicateOwnerException.class,
                    () -> service.create("John Smith", "Montreal"));

            verify(repo, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Read owner")
    class ReadOwnerTests {

        @Test
        void shouldReturnOwnerWhenGettingById() {

            UUID id = UUID.randomUUID();
            Owner owner = sampleOwner(id, "John Smith", "Montreal");

            when(repo.findById(OwnerId.of(id)))
                    .thenReturn(Optional.of(owner));

            Owner found = service.getById(id);

            assertThat(found).isSameAs(owner);

            verify(repo).findById(OwnerId.of(id));
        }

        @Test
        void shouldThrowOwnerNotFoundException() {

            UUID id = UUID.randomUUID();

            when(repo.findById(OwnerId.of(id)))
                    .thenReturn(Optional.empty());

            assertThrows(OwnerNotFoundException.class,
                    () -> service.getById(id));

            verify(repo).findById(OwnerId.of(id));
        }

        @Test
        void shouldReturnAllOwners() {

            List<Owner> owners = List.of(
                    sampleOwner(UUID.randomUUID(), "John Smith", "Montreal"),
                    sampleOwner(UUID.randomUUID(), "Jane Smith", "Laval")
            );

            when(repo.findAll()).thenReturn(owners);

            List<Owner> result = service.list();

            assertThat(result).hasSize(2);
            assertThat(result).containsExactlyElementsOf(owners);

            verify(repo).findAll();
        }
    }

    @Nested
    @DisplayName("Update owner")
    class UpdateOwnerTests {

        @Test
        void shouldUpdateOwnerSuccessfully() {

            UUID id = UUID.randomUUID();
            Owner owner = sampleOwner(id, "John Smith", "Montreal");

            when(repo.findById(OwnerId.of(id)))
                    .thenReturn(Optional.of(owner));

            when(repo.save(any(Owner.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Owner updated = service.update(id, "Jane Smith", "Laval");

            assertThat(updated.fullName().value()).isEqualTo("Jane Smith");
            assertThat(updated.address().value()).isEqualTo("Laval");

            verify(repo).save(owner);
        }
    }

    @Nested
    @DisplayName("Activate/deactivate owner")
    class StatusTests {

        @Test
        void shouldDeactivateOwnerSuccessfully() {

            UUID id = UUID.randomUUID();
            Owner owner = sampleOwner(id, "John Smith", "Montreal");

            when(repo.findById(OwnerId.of(id)))
                    .thenReturn(Optional.of(owner));

            when(repo.save(any(Owner.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Owner updated = service.deactivate(id);

            assertThat(updated.status()).isEqualTo(OwnerStatus.INACTIVE);
        }

        @Test
        void shouldActivateOwnerSuccessfully() {

            UUID id = UUID.randomUUID();
            Owner owner = sampleOwner(id, "John Smith", "Montreal");
            owner.deactivate();

            when(repo.findById(OwnerId.of(id)))
                    .thenReturn(Optional.of(owner));

            when(repo.save(any(Owner.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Owner updated = service.activate(id);

            assertThat(updated.status()).isEqualTo(OwnerStatus.ACTIVE);
        }
    }

    @Nested
    @DisplayName("Delete owner")
    class DeleteOwnerTests {

        @Test
        void shouldDeleteOwnerSuccessfully() {

            UUID id = UUID.randomUUID();
            Owner owner = sampleOwner(id, "John Smith", "Montreal");

            when(repo.findById(OwnerId.of(id)))
                    .thenReturn(Optional.of(owner));

            service.delete(id);

            verify(repo).deleteById(OwnerId.of(id));
        }

        @Test
        void shouldThrowWhenDeletingMissingOwner() {

            UUID id = UUID.randomUUID();

            when(repo.findById(OwnerId.of(id)))
                    .thenReturn(Optional.empty());

            assertThrows(OwnerNotFoundException.class,
                    () -> service.delete(id));

            verify(repo, never()).deleteById(any());
        }
    }
}