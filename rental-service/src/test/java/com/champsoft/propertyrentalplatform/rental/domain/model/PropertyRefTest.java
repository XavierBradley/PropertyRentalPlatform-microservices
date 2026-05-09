package com.champsoft.propertyrentalplatform.rental.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyRefTest {

    @Test
    @DisplayName("Should create property ref")
    void shouldCreatePropertyRef() {

        UUID id = UUID.randomUUID();

        PropertyRef ref = new PropertyRef(id);

        assertThat(ref.value()).isEqualTo(id);
    }

    @Test
    @DisplayName("Should throw when property ref is null")
    void shouldThrowWhenPropertyRefIsNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new PropertyRef(null)
        );
    }
}