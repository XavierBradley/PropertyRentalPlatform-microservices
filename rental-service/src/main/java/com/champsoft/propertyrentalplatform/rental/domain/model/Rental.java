package com.champsoft.propertyrentalplatform.rental.domain.model;

import com.champsoft.propertyrentalplatform.rental.domain.exception.RentalNotActiveException;

import java.util.UUID;

public class Rental {

    private final RentalId id;
    private final PropertyRef propertyId;
    private final OwnerRef ownerId;
    private final TenantRef tenantId;

    private Rent rent;
    private ExpiryDate expiry;
    private RentalStatus status;

    // CREATE NEW RENTAL
    public Rental(
            RentalId id,
            PropertyRef propertyId,
            OwnerRef ownerId,
            TenantRef tenantId,
            Rent rent,
            ExpiryDate expiry
    ) {
        this.id = id;
        this.propertyId = propertyId;
        this.ownerId = ownerId;
        this.tenantId = tenantId;
        this.rent = rent;
        this.expiry = expiry;
        this.status = RentalStatus.ACTIVE;
    }

    // REHYDRATION
    public Rental(
            RentalId id,
            PropertyRef propertyId,
            OwnerRef ownerId,
            TenantRef tenantId,
            Rent rent,
            ExpiryDate expiry,
            RentalStatus status
    ) {
        this.id = id;
        this.propertyId = propertyId;
        this.ownerId = ownerId;
        this.tenantId = tenantId;
        this.rent = rent;
        this.expiry = expiry;
        this.status = status;
    }

    public static Rental rehydrate(
            RentalId id,
            PropertyRef propertyId,
            OwnerRef ownerId,
            TenantRef tenantId,
            Rent rent,
            ExpiryDate expiry,
            RentalStatus status
    ) {
        return new Rental(id, propertyId, ownerId, tenantId, rent, expiry, status);
    }

    // GETTERS
    public RentalId id() { return id; }
    public PropertyRef propertyId() { return propertyId; }
    public OwnerRef ownerId() { return ownerId; }
    public TenantRef tenantId() { return tenantId; }
    public Rent rent() { return rent; }
    public ExpiryDate expiry() { return expiry; }
    public RentalStatus status() { return status; }

    public UUID propertyIdValue() { return propertyId.value(); }
    public UUID ownerIdValue() { return ownerId.value(); }
    public UUID tenantIdValue() { return tenantId.value(); }
    public double rentValue() { return rent.amount(); }
    public java.time.LocalDate expiryValue() { return expiry.value(); }

    // DOMAIN RULES

    public void renew(ExpiryDate newExpiry) {
        if (status == RentalStatus.EXPIRED) {
            throw new RentalNotActiveException("Cannot renew expired rental");
        }

        this.expiry = newExpiry;
        this.status = RentalStatus.ACTIVE;
    }

    public void cancel() {
        if (status == RentalStatus.EXPIRED) {
            throw new RentalNotActiveException("Rental already expired");
        }

        this.status = RentalStatus.EXPIRED;
    }
}