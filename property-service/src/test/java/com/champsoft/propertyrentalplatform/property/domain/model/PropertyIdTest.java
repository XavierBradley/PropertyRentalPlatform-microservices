package com.champsoft.propertyrentalplatform.property.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// Domain test → pure value object behavior
class PropertyIdTest {

    @Test
    void shouldCreatePropertyIdFromValue() {

        // ------------------- Act -------------------
        PropertyId id = PropertyId.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        // ------------------- Assert -------------------
        assertThat(id.value()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        assertThat(id.toString()).isEqualTo("00000000-0000-0000-0000-000000000001");
    }

    @Test
    void shouldCreateNewPropertyId() {

        // ------------------- Act -------------------
        PropertyId id = PropertyId.newId();

        // ------------------- Assert -------------------
        assertThat(id).isNotNull();
        assertThat(id.value()).isNotNull();
    }

    @Test
    void shouldComparePropertyIdsCorrectly() {

        // ------------------- Arrange -------------------
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000001");

        PropertyId id1 = PropertyId.of(uuid);
        PropertyId id2 = PropertyId.of(uuid);
        PropertyId id3 = PropertyId.newId();

        // ------------------- Assert -------------------
        assertThat(id1).isEqualTo(id2);
        assertThat(id1).isNotEqualTo(id3);
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
    }
}