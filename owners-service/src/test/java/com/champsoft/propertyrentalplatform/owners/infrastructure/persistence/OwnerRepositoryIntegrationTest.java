package com.champsoft.propertyrentalplatform.owners.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest

@ActiveProfiles("testing")
class OwnerRepositoryIntegrationTest {

    @Autowired
    private SpringDataOwnerRepository repository;

    @Test
    @DisplayName("Should save an owner successfully")
    void shouldSaveOwnerSuccessfully() {

        UUID id = UUID.randomUUID();

        OwnerJpaEntity owner = new OwnerJpaEntity();
        owner.id = id;
        owner.fullName = "John Smith";
        owner.address = "Montreal";

        owner.status = "ACTIVE";

        OwnerJpaEntity saved = repository.save(owner);

        assertThat(saved).isNotNull();
        assertThat(saved.id).isEqualTo(id);
        assertThat(saved.fullName).isEqualTo("John Smith");
        assertThat(saved.address).isEqualTo("Montreal");
        assertThat(saved.status).isEqualTo("ACTIVE");
    }

    @Test
    @DisplayName("Should find an owner by id")
    void shouldFindOwnerById() {

        UUID id = UUID.randomUUID();

        OwnerJpaEntity owner = new OwnerJpaEntity();
        owner.id = id;
        owner.fullName = "Alice Brown";
        owner.address = "Laval";
        owner.status = "ACTIVE";

        repository.save(owner);

        Optional<OwnerJpaEntity> found = repository.findById(id);

        assertThat(found).isPresent();
        assertThat(found.get().id).isEqualTo(id);
        assertThat(found.get().fullName).isEqualTo("Alice Brown");
        assertThat(found.get().address).isEqualTo("Laval");
        assertThat(found.get().status).isEqualTo("ACTIVE");
    }

    @Test
    @DisplayName("Should return true when owner full name exists ignoring case")
    void shouldReturnTrueWhenOwnerFullNameExistsIgnoringCase() {

        UUID id = UUID.randomUUID();

        OwnerJpaEntity owner = new OwnerJpaEntity();
        owner.id = id;
        owner.fullName = "Bob Martin";
        owner.address = "Longueuil";
        owner.status = "ACTIVE";

        repository.save(owner);

        boolean exists = repository.existsByFullNameIgnoreCase("bob martin");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when owner full name does not exist")
    void shouldReturnFalseWhenOwnerFullNameDoesNotExist() {

        boolean exists = repository.existsByFullNameIgnoreCase("NOT-EXISTING");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should delete an owner successfully")
    void shouldDeleteOwnerSuccessfully() {

        UUID id = UUID.randomUUID();

        OwnerJpaEntity owner = new OwnerJpaEntity();
        owner.id = id;
        owner.fullName = "Delete Me";
        owner.address = "Brossard";
        owner.status = "ACTIVE";

        repository.save(owner);

        repository.deleteById(id);

        Optional<OwnerJpaEntity> found = repository.findById(id);

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should return empty when owner id is not found")
    void shouldReturnEmptyWhenOwnerIdIsNotFound() {

        Optional<OwnerJpaEntity> found = repository.findById(UUID.fromString("UNKNOWN-ID"));

        assertThat(found).isEmpty();
    }
}