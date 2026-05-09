package com.champsoft.propertyrentalplatform.tenant.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TenantIdTest {

    @Test
    @DisplayName("Should create tenant id from UUID")
    void shouldCreateTenantIdFromUuid() {

        UUID uuid = UUID.randomUUID();

        TenantId id = TenantId.of(uuid);

        assertThat(id.value()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("Should generate new tenant id")
    void shouldGenerateNewTenantId() {

        TenantId id = TenantId.newId();

        assertThat(id.value()).isNotNull();
    }

    @Test
    @DisplayName("Should support equality")
    void shouldSupportEquality() {

        UUID uuid = UUID.randomUUID();

        TenantId id1 = TenantId.of(uuid);

        TenantId id2 = TenantId.of(uuid);

        assertThat(id1).isEqualTo(id2);

        assertThat(id1.hashCode())
                .isEqualTo(id2.hashCode());
    }

    @Test
    @DisplayName("Should support toString")
    void shouldSupportToString() {

        UUID uuid = UUID.randomUUID();

        TenantId id = TenantId.of(uuid);

        assertThat(id.toString())
                .isEqualTo(uuid.toString());
    }
}