package com.champsoft.propertyrentalplatform.property.application.service;

import com.champsoft.propertyrentalplatform.property.application.exception.PropertyNotFoundException;
import com.champsoft.propertyrentalplatform.property.application.port.out.PropertyRepositoryPort;
import com.champsoft.propertyrentalplatform.property.domain.model.Address;
import com.champsoft.propertyrentalplatform.property.domain.model.Property;
import com.champsoft.propertyrentalplatform.property.domain.model.PropertyId;
import com.champsoft.propertyrentalplatform.property.domain.model.PropertyTax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PropertyEligibilityServiceTest {

    private PropertyRepositoryPort repo;

    private PropertyEligibilityService service;

    @BeforeEach
    void setUp() {

        repo = mock(PropertyRepositoryPort.class);

        service = new PropertyEligibilityService(repo);
    }

    private Property property(UUID id) {

        return new Property(
                PropertyId.of(id),
                new PropertyTax(0.01),
                new Address("123 Main Street")
        );
    }

    @Test
    @DisplayName("Should return true when property is available")
    void shouldReturnTrueWhenPropertyIsAvailable() {

        UUID id = UUID.randomUUID();

        Property property = property(id);

        when(repo.findById(PropertyId.of(id)))
                .thenReturn(Optional.of(property));

        boolean result = service.isEligible(id);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should return false when property is unavailable")
    void shouldReturnFalseWhenPropertyIsUnavailable() {

        UUID id = UUID.randomUUID();

        Property property = property(id);

        property.rent();

        when(repo.findById(PropertyId.of(id)))
                .thenReturn(Optional.of(property));

        boolean result = service.isEligible(id);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should throw when property does not exist")
    void shouldThrowWhenPropertyDoesNotExist() {

        UUID id = UUID.randomUUID();

        when(repo.findById(PropertyId.of(id)))
                .thenReturn(Optional.empty());

        assertThrows(
                PropertyNotFoundException.class,
                () -> service.isEligible(id)
        );
    }
}