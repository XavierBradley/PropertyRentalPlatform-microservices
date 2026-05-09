package com.champsoft.propertyrentalplatform.rental.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TenantRefTest {

    @Test
    @DisplayName("Should create tenant ref")
    void shouldCreateTenantRef() {

        UUID id = UUID.randomUUID();

        TenantRef ref = new TenantRef(id);

        assertThat(ref.value()).isEqualTo(id);
    }

    @Test
    @DisplayName("Should throw when tenant ref is null")
    void shouldThrowWhenTenantRefIsNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new TenantRef(null)
        );
    }
}