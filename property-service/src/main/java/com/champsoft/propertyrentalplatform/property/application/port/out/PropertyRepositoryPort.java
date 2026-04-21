package com.champsoft.propertyrentalplatform.property.application.port.out;

import com.champsoft.propertyrentalplatform.property.domain.model.Address;
import com.champsoft.propertyrentalplatform.property.domain.model.Property;
import com.champsoft.propertyrentalplatform.property.domain.model.PropertyId;

import java.util.List;
import java.util.Optional;

public interface PropertyRepositoryPort {
    Property save(Property property);
    Optional<Property> findById(PropertyId id);
    Optional<Property> findByAddress(Address address);
    boolean existsByAddress(Address address);
    List<Property> findAll();
    void deleteById(PropertyId id);
}
