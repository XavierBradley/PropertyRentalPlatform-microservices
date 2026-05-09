package com.champsoft.propertyrentalplatform.owners.domain.model;


import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// Domain test → pure business rule testing
// NO Spring, NO Mockito, NO database
class OwnerTest {

    @Test
    void shouldCreateOwnerWithActiveStatus() {

        UUID id = UUID.randomUUID();

        Owner owner = new Owner(
                OwnerId.of(id),
                new FullName("John Smith"),
                new Address("Montreal")
        );

        assertThat(owner.id().value()).isEqualTo(id);

        assertThat(owner.fullName().value()).isEqualTo("John Smith");

        assertThat(owner.address().value()).isEqualTo("Montreal");

        assertThat(owner.status()).isEqualTo(OwnerStatus.ACTIVE);

        assertThat(owner.isEligibleForRegistration()).isTrue();
    }

    @Test
    void shouldDeactivateOwnerSuccessfully() {

        UUID id = UUID.randomUUID();

        Owner owner = new Owner(
                OwnerId.of(id),
                new FullName("John Smith"),
                new Address("Montreal")
        );

        owner.deactivate();

        assertThat(owner.status()).isEqualTo(OwnerStatus.INACTIVE);

        assertThat(owner.isEligibleForRegistration()).isFalse();
    }

    @Test
    void shouldUpdateOwnerSuccessfully() {

        UUID id = UUID.randomUUID();

        Owner owner = new Owner(
                OwnerId.of(id),
                new FullName("John Smith"),
                new Address("Montreal")
        );

        owner.update(new FullName("Jane Smith"), new Address("Laval"));

        assertThat(owner.fullName().value()).isEqualTo("Jane Smith");

        assertThat(owner.address().value()).isEqualTo("Laval");
    }
}