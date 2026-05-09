package com.champsoft.propertyrentalplatform.property.application.service;

import com.champsoft.propertyrentalplatform.property.application.exception.DuplicateAddressException;
import com.champsoft.propertyrentalplatform.property.application.exception.PropertyNotFoundException;
import com.champsoft.propertyrentalplatform.property.application.port.out.PropertyRepositoryPort;
import com.champsoft.propertyrentalplatform.property.domain.exception.PropertyAlreadyBeingRentedException;
import com.champsoft.propertyrentalplatform.property.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PropertyCrudServiceTest {

    private PropertyRepositoryPort repo;

    private PropertyCrudService service;

    @BeforeEach
    void setUp() {

        repo = mock(PropertyRepositoryPort.class);

        service = new PropertyCrudService(repo);
    }

    @Test
    @DisplayName("Should create property")
    void shouldCreateProperty() {

        when(repo.existsByAddress(any(Address.class)))
                .thenReturn(false);

        when(repo.save(any(Property.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Property result =
                service.create(0.01, "123 Main Street");

        assertThat(result.id()).isNotNull();

        assertThat(result.tax().value())
                .isEqualTo(0.01);

        assertThat(result.address().value())
                .isEqualTo("123 Main Street");

        assertThat(result.status())
                .isEqualTo(PropertyStatus.AVAILABLE);

        verify(repo).save(any(Property.class));
    }

    @Test
    @DisplayName("Should throw when address already exists")
    void shouldThrowWhenAddressAlreadyExists() {

        when(repo.existsByAddress(any(Address.class)))
                .thenReturn(true);

        assertThrows(
                DuplicateAddressException.class,
                () -> service.create(0.01, "123 Main Street")
        );

        verify(repo, never()).save(any());
    }

    @Test
    @DisplayName("Should get property by id")
    void shouldGetPropertyById() {

        UUID id = UUID.randomUUID();

        Property property = new Property(
                PropertyId.of(id),
                new PropertyTax(0.01),
                new Address("123 Main Street")
        );

        when(repo.findById(PropertyId.of(id)))
                .thenReturn(Optional.of(property));

        Property result = service.getById(id);

        assertThat(result).isEqualTo(property);
    }

    @Test
    @DisplayName("Should throw when property not found by id")
    void shouldThrowWhenPropertyNotFoundById() {

        UUID id = UUID.randomUUID();

        when(repo.findById(PropertyId.of(id)))
                .thenReturn(Optional.empty());

        assertThrows(
                PropertyNotFoundException.class,
                () -> service.getById(id)
        );
    }

    @Test
    @DisplayName("Should get property by address")
    void shouldGetPropertyByAddress() {

        Property property = new Property(
                PropertyId.newId(),
                new PropertyTax(0.01),
                new Address("123 Main Street")
        );

        when(repo.findByAddress(any(Address.class)))
                .thenReturn(Optional.of(property));

        Property result =
                service.getByAddress("123 Main Street");

        assertThat(result).isEqualTo(property);
    }

    @Test
    @DisplayName("Should throw when property not found by address")
    void shouldThrowWhenPropertyNotFoundByAddress() {

        when(repo.findByAddress(any(Address.class)))
                .thenReturn(Optional.empty());

        assertThrows(
                PropertyNotFoundException.class,
                () -> service.getByAddress("123 Main Street")
        );
    }

    @Test
    @DisplayName("Should list properties")
    void shouldListProperties() {

        List<Property> properties = List.of(
                new Property(
                        PropertyId.newId(),
                        new PropertyTax(0.01),
                        new Address("123 Main Street")
                ),
                new Property(
                        PropertyId.newId(),
                        new PropertyTax(0.02),
                        new Address("456 Park Avenue")
                )
        );

        when(repo.findAll()).thenReturn(properties);

        List<Property> result = service.list();

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("Should update property")
    void shouldUpdateProperty() {

        UUID id = UUID.randomUUID();

        Property property = new Property(
                PropertyId.of(id),
                new PropertyTax(0.01),
                new Address("123 Main Street")
        );

        when(repo.findById(PropertyId.of(id)))
                .thenReturn(Optional.of(property));

        when(repo.save(any(Property.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Property updated =
                service.update(
                        id,
                        0.02,
                        "456 Park Avenue"
                );

        assertThat(updated.tax().value())
                .isEqualTo(0.02);

        assertThat(updated.address().value())
                .isEqualTo("456 Park Avenue");

        verify(repo).save(property);
    }

    @Test
    @DisplayName("Should activate property")
    void shouldActivateProperty() {

        UUID id = UUID.randomUUID();

        Property property = new Property(
                PropertyId.of(id),
                new PropertyTax(0.01),
                new Address("123 Main Street")
        );

        when(repo.findById(PropertyId.of(id)))
                .thenReturn(Optional.of(property));

        when(repo.save(any(Property.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Property result = service.activate(id);

        assertThat(result.status())
                .isEqualTo(PropertyStatus.UNAVAILABLE);

        verify(repo).save(property);
    }

    @Test
    @DisplayName("Should throw when activating rented property")
    void shouldThrowWhenActivatingRentedProperty() {

        UUID id = UUID.randomUUID();

        Property property = new Property(
                PropertyId.of(id),
                new PropertyTax(0.01),
                new Address("123 Main Street")
        );

        property.rent();

        when(repo.findById(PropertyId.of(id)))
                .thenReturn(Optional.of(property));

        assertThrows(
                PropertyAlreadyBeingRentedException.class,
                () -> service.activate(id)
        );
    }

    @Test
    @DisplayName("Should delete property")
    void shouldDeleteProperty() {

        UUID id = UUID.randomUUID();

        Property property = new Property(
                PropertyId.of(id),
                new PropertyTax(0.01),
                new Address("123 Main Street")
        );

        when(repo.findById(PropertyId.of(id)))
                .thenReturn(Optional.of(property));

        service.delete(id);

        verify(repo).deleteById(PropertyId.of(id));
    }

    @Test
    @DisplayName("Should pass correct address object to repository")
    void shouldPassCorrectAddressObjectToRepository() {

        when(repo.existsByAddress(any(Address.class)))
                .thenReturn(false);

        when(repo.save(any(Property.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        service.create(0.01, "123 Main Street");

        ArgumentCaptor<Address> captor =
                ArgumentCaptor.forClass(Address.class);

        verify(repo).existsByAddress(captor.capture());

        assertThat(captor.getValue().value())
                .isEqualTo("123 Main Street");
    }
}