package com.champsoft.propertyrentalplatform.property.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyIdTest {

    @Test
    @DisplayName("Should create property id from UUID")
    void shouldCreatePropertyIdFromUuid() {

        UUID uuid = UUID.randomUUID();

        PropertyId id = PropertyId.of(uuid);

        assertThat(id.value()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("Should generate new property id")
    void shouldGenerateNewPropertyId() {

        PropertyId id = PropertyId.newId();

        assertThat(id.value()).isNotNull();
    }

    @Test
    @DisplayName("Should support equality")
    void shouldSupportEquality() {

        UUID uuid = UUID.randomUUID();

        PropertyId id1 = PropertyId.of(uuid);

        PropertyId id2 = PropertyId.of(uuid);

        assertThat(id1).isEqualTo(id2);
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
    }

    @Test
    @DisplayName("Should support toString")
    void shouldSupportToString() {

        UUID uuid = UUID.randomUUID();

        PropertyId id = PropertyId.of(uuid);

        assertThat(id.toString())
                .isEqualTo(uuid.toString());
    }
}