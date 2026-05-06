package com.champsoft.propertyrentalplatform.rental.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Domain test → value object validation
class PropertyRefTest {

    @Test
    void shouldCreateValidPropertyRef() {

        // ------------------- Act -------------------
        PropertyRef ref = new PropertyRef(UUID.randomUUID());

        // ------------------- Assert -------------------
        assertThat(ref.value()).isNotNull();
    }

    @Test
    void shouldRejectNullPropertyRef() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new PropertyRef(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}