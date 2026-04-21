package com.champsoft.propertyrentalplatform.property.domain.model;

import com.champsoft.propertyrentalplatform.property.domain.exception.InvalidPropertyYearException;

public record PropertySpecs(String make, String model, int year) {
    public PropertySpecs {
        if (make == null || make.trim().isEmpty()) throw new IllegalArgumentException("make is required");
        if (model == null || model.trim().isEmpty()) throw new IllegalArgumentException("model is required");
        if (year < 1980 || year > 2050) throw new InvalidPropertyYearException("year must be between 1980 and 2050");
    }
}
