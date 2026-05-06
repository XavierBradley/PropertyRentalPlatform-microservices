package com.champsoft.propertyrentalplatform.property.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;

// @DataJpaTest → loads only JPA components (repositories, entities)
// It starts an in-memory database (H2) automatically
@DataJpaTest

// Use testing profile → ensures we use H2 instead of PostgreSQL
@ActiveProfiles("testing")
class PropertyRepositoryIntegrationTest {

    // Real repository (NOT mocked)
    @Autowired
    private SpringDataPropertyRepository repository;

    @Test
    @DisplayName("Should save a property successfully")
    void shouldSavePropertySuccessfully() {

        // ------------------- Arrange -------------------
        PropertyJpaEntity property = new PropertyJpaEntity();
        property.id = UUID.fromString("00000000-0000-0000-0000-000000000001");
        property.address = "123 Maple St, Montreal";
        property.tax = 0.015;
        property.status = "AVAILABLE";

        // ------------------- Act -------------------
        PropertyJpaEntity saved = repository.save(property);

        // ------------------- Assert -------------------
        assertThat(saved).isNotNull();
        assertThat(saved.id).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        assertThat(saved.address).isEqualTo("123 Maple St, Montreal");
        assertThat(saved.tax).isEqualTo(0.015);
        assertThat(saved.status).isEqualTo("AVAILABLE");
    }

    @Test
    @DisplayName("Should find a property by address")
    void shouldFindPropertyByAddress() {

        // ------------------- Arrange -------------------
        PropertyJpaEntity property = new PropertyJpaEntity();
        property.id = UUID.fromString("00000000-0000-0000-0000-000000000002");
        property.address = "456 Queen St, Toronto";
        property.tax = 0.02;
        property.status = "AVAILABLE";

        repository.save(property);

        // ------------------- Act -------------------
        Optional<PropertyJpaEntity> found =
                repository.findByAddress("456 Queen St, Toronto");

        // ------------------- Assert -------------------
        assertThat(found).isPresent();
        assertThat(found.get().id).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        assertThat(found.get().address).isEqualTo("456 Queen St, Toronto");
        assertThat(found.get().tax).isEqualTo(0.02);
        assertThat(found.get().status).isEqualTo("AVAILABLE");
    }

    @Test
    @DisplayName("Should return true when property exists by address")
    void shouldReturnTrueWhenPropertyExists() {

        // ------------------- Arrange -------------------
        PropertyJpaEntity property = new PropertyJpaEntity();
        property.id = UUID.fromString("00000000-0000-0000-0000-000000000003");
        property.address = "789 King St, Vancouver";
        property.tax = 0.018;
        property.status = "AVAILABLE";

        repository.save(property);

        // ------------------- Act -------------------
        boolean exists = repository.existsByAddress("789 King St, Vancouver");

        // ------------------- Assert -------------------
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when property does not exist")
    void shouldReturnFalseWhenPropertyDoesNotExist() {

        // ------------------- Act -------------------
        boolean exists = repository.existsByAddress("UNKNOWN ADDRESS");

        // ------------------- Assert -------------------
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should delete a property successfully")
    void shouldDeletePropertySuccessfully() {

        // ------------------- Arrange -------------------
        PropertyJpaEntity property = new PropertyJpaEntity();
        property.id = UUID.fromString("00000000-0000-0000-0000-000000000004");
        property.address = "111 Delete St, Ottawa";
        property.tax = 0.012;
        property.status = "AVAILABLE";

        repository.save(property);

        // ------------------- Act -------------------
        repository.deleteById(UUID.fromString("00000000-0000-0000-0000-000000000004"));

        Optional<PropertyJpaEntity> found =
                repository.findById(UUID.fromString("00000000-0000-0000-0000-000000000004"));

        // ------------------- Assert -------------------
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should return empty when property is not found by address")
    void shouldReturnEmptyWhenPropertyNotFoundByAddress() {

        // ------------------- Act -------------------
        Optional<PropertyJpaEntity> found =
                repository.findByAddress("NON EXISTENT ADDRESS");

        // ------------------- Assert -------------------
        assertThat(found).isEmpty();
    }
}