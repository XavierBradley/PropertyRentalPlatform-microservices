package com.champsoft.propertyrentalplatform.rental.infrastructure.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "rentals")
public class RentalJpaEntity {
    @Id
    public UUID id;

    @Column(name = "property_id", nullable = false)
    public UUID propertyId;

    @Column(name = "owner_id", nullable = false)
    public UUID ownerId;

    @Column(name = "tenant_id", nullable = false)
    public UUID tenantId;

    @Column(nullable = false)
    public BigDecimal rent;

    @Column(nullable = false)
    public LocalDate expiry;

    @Column(nullable = false)
    public String status;
}
