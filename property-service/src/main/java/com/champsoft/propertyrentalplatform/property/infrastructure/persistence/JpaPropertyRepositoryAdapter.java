package com.champsoft.propertyrentalplatform.property.infrastructure.persistence;

import com.champsoft.propertyrentalplatform.property.application.port.out.PropertyRepositoryPort;
import com.champsoft.propertyrentalplatform.property.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaPropertyRepositoryAdapter implements PropertyRepositoryPort {

    private final SpringDataPropertyRepository jpa;

    public JpaPropertyRepositoryAdapter(SpringDataPropertyRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Property save(Property property) {
        var e = toEntity(property);
        jpa.save(e);
        return property;
    }

    @Override
    public Optional<Property> findById(PropertyId id) {
        return jpa.findById(id.value()).map(this::toDomain);
    }

    @Override
    public Optional<Property> findByAddress(Address address) {
        return jpa.findByAddress(address.value()).map(this::toDomain);
    }

    @Override
    public boolean existsByAddress(Address address) {
        return jpa.existsByAddress(address.value());
    }

    @Override
    public List<Property> findAll() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(PropertyId id) {
        jpa.deleteById(id.value());
    }

    private PropertyJpaEntity toEntity(Property v) {
        var e = new PropertyJpaEntity();
        e.id = v.id().value();
        e.tax = v.tax().value();
        e.address = v.address().value();
        e.status = v.status().name();
        return e;
    }

    private Property toDomain(PropertyJpaEntity e) {
        var property = new Property(
                PropertyId.of(e.id),
                new PropertyTax(e.tax),
                new Address(e.address)

        );

        if ("ACTIVE".equalsIgnoreCase(e.status)) {
            property.rent();
        }

        return property;
    }
}
