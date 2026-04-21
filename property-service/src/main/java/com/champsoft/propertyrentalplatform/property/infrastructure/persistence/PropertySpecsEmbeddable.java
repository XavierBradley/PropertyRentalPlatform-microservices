package com.champsoft.propertyrentalplatform.property.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PropertySpecsEmbeddable {

    @Column(name = "make", nullable = false)
    public String make;

    @Column(name = "model", nullable = false)
    public String model;

    @Column(name = "vehicle_year", nullable = false)
    public int year;

    protected PropertySpecsEmbeddable() {
    }

    public PropertySpecsEmbeddable(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }
}
