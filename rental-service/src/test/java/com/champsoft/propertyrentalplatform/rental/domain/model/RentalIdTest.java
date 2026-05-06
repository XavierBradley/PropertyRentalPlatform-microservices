package com.champsoft.propertyrentalplatform.rental.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// Domain test → value object behavior
class RentalIdTest {

    @Test
    void shouldCreateRentalIdFromValue() {

        // ------------------- Act -------------------
        RentalId id = RentalId.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        // ------------------- Assert -------------------
        assertThat(id.value()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    void shouldCreateNewRentalId() {

        // ------------------- Act -------------------
        RentalId id = RentalId.newId();

        // ------------------- Assert -------------------
        assertThat(id.value()).isNotNull();
    }
}