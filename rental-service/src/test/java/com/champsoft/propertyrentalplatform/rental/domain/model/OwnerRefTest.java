package com.champsoft.propertyrentalplatform.rental.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Domain test → validation rules
class OwnerRefTest {

    @Test
    void shouldRejectNullOwnerRef() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new OwnerRef(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}