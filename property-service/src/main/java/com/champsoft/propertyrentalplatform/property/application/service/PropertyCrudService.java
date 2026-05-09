package com.champsoft.propertyrentalplatform.property.application.service;

import com.champsoft.propertyrentalplatform.property.application.exception.DuplicateAddressException;
import com.champsoft.propertyrentalplatform.property.application.exception.PropertyNotFoundException;
import com.champsoft.propertyrentalplatform.property.application.port.out.PropertyRepositoryPort;
import com.champsoft.propertyrentalplatform.property.domain.model.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropertyCrudService {

    private final PropertyRepositoryPort repo;

    public PropertyCrudService(PropertyRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional
    public Property create(double tax, String address) {
        var a = new Address(address);
        if (repo.existsByAddress(a)) throw new DuplicateAddressException("Another property already has this same address: " + a.value());

        var property = new Property(PropertyId.newId(), new PropertyTax(tax), a);
        return repo.save(property);
    }

    @Transactional(readOnly = true)
    public Property getById(UUID id) {
        return repo.findById(PropertyId.of(id))
                .orElseThrow(() -> new PropertyNotFoundException("Property not found: " + id));
    }

    @Transactional(readOnly = true)
    public Property getByAddress(String address) {
        return repo.findByAddress(new Address(address))
                .orElseThrow(() -> new PropertyNotFoundException("Property not found by address: " + address));
    }

    @Transactional(readOnly = true)
    public List<Property> list() {
        return repo.findAll();
    }

    @Transactional
    public Property update(UUID id, double tax, String address) {
        var property = getById(id);

        var a = new Address(address);

        property.update(new PropertyTax(tax), a);
        return repo.save(property);
    }

    @Transactional
    public Property activate(UUID id) {
        var property = getById(id);
        property.rent();
        return repo.save(property);
    }

    @Transactional
    public void delete(UUID id) {
        getById(id);
        repo.deleteById(PropertyId.of(id));
    }
}
