package com.champsoft.propertyrentalplatform.property.domain.model;

import com.champsoft.propertyrentalplatform.property.domain.exception.InvalidPropertyTaxException;

public final class PropertyTax {
    private final double value;

    public PropertyTax(double value) {
        if (value <= 0) throw new InvalidPropertyTaxException("property tax is required");

        if (value < 0.003 || value > 0.025) {
            throw new InvalidPropertyTaxException("property tax must be between 0.3% and 2.5%");
        }
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override public String toString() { return String.valueOf(value); }
}
