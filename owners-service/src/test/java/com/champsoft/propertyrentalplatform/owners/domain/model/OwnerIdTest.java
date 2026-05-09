package com.champsoft.propertyrentalplatform.owners.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerIdTest {

    @Test
    void shouldCreateOwnerIdFromValue() {

        UUID uuid = UUID.randomUUID();

        OwnerId id = OwnerId.of(uuid);

        assertThat(id.value()).isEqualTo(uuid);

        assertThat(id.toString()).isEqualTo(String.valueOf(uuid));
    }

    @Test
    void shouldCreateNewOwnerId() {

        OwnerId id = OwnerId.newId();

        assertThat(id).isNotNull();

        assertThat(id.value()).isNotIn();
    }

    @Test
    void shouldCompareOwnerIdsCorrectly() {

        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        OwnerId id1 = OwnerId.of(uuid1);
        OwnerId id2 = OwnerId.of(uuid1);

        OwnerId id3 = OwnerId.of(uuid2);

        assertThat(id1).isEqualTo(id2);

        assertThat(id1).isNotEqualTo(id3);

        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
    }
}