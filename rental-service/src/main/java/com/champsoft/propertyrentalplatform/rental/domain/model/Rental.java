package com.champsoft.propertyrentalplatform.rental.domain.model;

import java.util.UUID;

public class Rental {
    private final RentalId id;
    private final PropertyRef propertyId;
    private final OwnerRef ownerId;
    private final TenantRef tenantId;

    private Rent rent;
    private ExpiryDate expiry;
    private RentalStatus status;

    public Rental(RentalId id, PropertyRef propertyId, OwnerRef ownerId, TenantRef tenantId,
                  Rent rent, ExpiryDate expiry) {
        this.id = id;
        this.propertyId = propertyId;
        this.ownerId = ownerId;
        this.tenantId = tenantId;
        this.rent = rent;
        this.expiry = expiry;
        this.status = RentalStatus.ACTIVE;
    }

    public RentalId id() { return id; }
    public PropertyRef propertyId() { return propertyId; }
    public OwnerRef ownerId() { return ownerId; }
    public TenantRef tenantId() { return tenantId; }
    public Rent rent() { return rent; }
    public ExpiryDate expiry() { return expiry; }
    public RentalStatus status() { return status; }

    // convenience accessors for API layer to avoid nested record accessor issues
    public UUID propertyIdValue() { return propertyId.value(); }
    public UUID ownerIdValue() { return ownerId.value(); }
    public UUID tenantIdValue() { return tenantId.value(); }
    public double rentValue() { return rent.amount(); }
    public java.time.LocalDate expiryValue() { return expiry.value(); }

    public void renew(ExpiryDate newExpiry) {
        if (status != RentalStatus.ACTIVE) throw new RuntimeException("Rental not ACTIVE");
        this.expiry = newExpiry;
    }

    public void cancel() {
        this.status = RentalStatus.EXPIRED;
    }
}
