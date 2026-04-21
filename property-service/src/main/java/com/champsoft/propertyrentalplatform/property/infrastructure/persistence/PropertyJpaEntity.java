package com.champsoft.propertyrentalplatform.property.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "properties")
public class PropertyJpaEntity {
    @Id
    public String id;

    @Column(name = "tax", nullable = false)
    public double tax;

    @Column(nullable = false, unique = true)
    public String address;

    @Column(nullable = false)
    public String status;
}