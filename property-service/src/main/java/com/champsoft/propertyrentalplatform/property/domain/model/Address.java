package com.champsoft.propertyrentalplatform.property.domain.model;

import com.champsoft.propertyrentalplatform.property.domain.exception.InvalidAddressException;

public final class Address {
    private final String value;

    public Address(String value) {
        if (value == null) {
            throw new InvalidAddressException("Address must exist");
        }
        String v = value.trim();
        if (v.length() > 200) throw new InvalidAddressException("Address max length is 200");
        this.value = v.isEmpty() ? null : v;
    }

    public String value() { return value; }
}
