package com.champsoft.propertyrentalplatform.property.domain.model;

import com.champsoft.propertyrentalplatform.property.domain.exception.PropertyAlreadyBeingRentedException;

public class Property {
    private final PropertyId id;
    private PropertyTax tax;
    private Address address;
    private PropertyStatus status;

    public Property(PropertyId id, PropertyTax tax, Address address) {
        this.id = id;
        this.tax = tax;
        this.address = address;
        this.status = PropertyStatus.AVAILABLE;
    }

    public PropertyId id() { return id; }
    public PropertyTax tax() { return tax; }
    public Address address() { return address; }
    public PropertyStatus status() { return status; }

    public void update(PropertyTax tax, Address address) {
        this.tax = tax;
        this.address = address;
    }

    public void rent() {
        if (status == PropertyStatus.UNAVAILABLE) {
            throw new PropertyAlreadyBeingRentedException("Property is already being rented to someone else");
        }
        this.status = PropertyStatus.UNAVAILABLE;
    }

    public boolean isEligibleToBeRented() {
        return status == PropertyStatus.AVAILABLE;
    }
}
