package com.champsoft.propertyrentalplatform.rental.infrastructure.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class RentalJpaEntity {
    @Id
    public String id;

    @Column(name = "property_id", nullable = false)
    public String propertyId;

    @Column(name = "owner_id", nullable = false)
    public String ownerId;

    @Column(name = "tenant_id", nullable = false)
    public String tenantId;

    @Column(nullable = false)
    public double rent;

    @Column(nullable = false)
    public LocalDate expiry;

    @Column(nullable = false)
    public String status;
}
