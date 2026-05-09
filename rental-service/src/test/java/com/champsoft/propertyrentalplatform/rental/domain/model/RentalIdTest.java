package com.champsoft.propertyrentalplatform.rental.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// Domain test → value object behavior
class RentalIdTest {

    @Test
    @DisplayName("Should create rental ID from valuer")
    void shouldCreateRentalIdFromValue() {

        RentalId id = RentalId.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertThat(id.value()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    @DisplayName("Should create new rental ID")
    void shouldCreateNewRentalId() {

        RentalId id = RentalId.newId();

        assertThat(id.value()).isNotNull();
    }
}