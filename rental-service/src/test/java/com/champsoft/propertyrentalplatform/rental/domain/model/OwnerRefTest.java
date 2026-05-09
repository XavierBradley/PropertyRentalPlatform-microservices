package com.champsoft.propertyrentalplatform.rental.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OwnerRefTest {

    @Test
    @DisplayName("Should create owner ref")
    void shouldCreateOwnerRef() {

        UUID id = UUID.randomUUID();

        OwnerRef ref = new OwnerRef(id);

        assertThat(ref.value()).isEqualTo(id);
    }

    @Test
    @DisplayName("Should throw when owner ref is null")
    void shouldThrowWhenOwnerRefIsNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new OwnerRef(null)
        );
    }
}