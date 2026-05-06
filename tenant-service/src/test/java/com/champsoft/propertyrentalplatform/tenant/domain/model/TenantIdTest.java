package com.champsoft.propertyrentalplatform.tenant.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// Domain test → pure value object behavior
// NO Spring, NO Mockito, NO database
class TenantIdTest {

    @Test
    void shouldCreateTenantIdFromValue() {

        // ------------------- Act -------------------
        TenantId id = TenantId.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        // ------------------- Assert -------------------
        assertThat(id.value()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        assertThat(id.toString()).isEqualTo("00000000-0000-0000-0000-000000000001");
    }

    @Test
    void shouldCreateNewTenantId() {

        // ------------------- Act -------------------
        TenantId id = TenantId.newId();

        // ------------------- Assert -------------------
        assertThat(id).isNotNull();
        assertThat(id.value()).isNotNull();
    }

    @Test
    void shouldCompareTenantIdsCorrectly() {

        // ------------------- Arrange -------------------
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000001");

        TenantId id1 = TenantId.of(uuid);
        TenantId id2 = TenantId.of(uuid);
        TenantId id3 = TenantId.newId();

        // ------------------- Assert -------------------
        assertThat(id1).isEqualTo(id2);
        assertThat(id1).isNotEqualTo(id3);
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
    }
}