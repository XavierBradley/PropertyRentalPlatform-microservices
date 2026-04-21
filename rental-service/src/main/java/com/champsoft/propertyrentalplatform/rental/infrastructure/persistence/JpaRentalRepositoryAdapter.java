package com.champsoft.propertyrentalplatform.rental.infrastructure.persistence;

import com.champsoft.propertyrentalplatform.rental.domain.model.*;
import com.champsoft.propertyrentalplatform.rental.application.port.out.RentalRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaRentalRepositoryAdapter implements RentalRepositoryPort {

    private final SpringDataRentalRepository jpa;
    public JpaRentalRepositoryAdapter(SpringDataRentalRepository jpa) { this.jpa = jpa; }

    @Override
    public Rental save(Rental reg) {
        jpa.save(toEntity(reg));
        return reg;
    }

    @Override
    public Optional<Rental> findById(RentalId id) {
        return jpa.findById(id.value()).map(this::toDomain);
    }

    @Override
    public List<Rental> findAll() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(RentalId id) {
        jpa.deleteById(id.value());
    }

    private RentalJpaEntity toEntity(Rental reg) {
        var e = new RentalJpaEntity();
        e.id = reg.id().value();
        e.propertyId = reg.propertyId().value();
        e.ownerId = reg.ownerId().value();
        e.tenantId = reg.tenantId().value();
        e.rent = reg.rent().amount();
        e.expiry = reg.expiry().value();
        e.status = reg.status().name();
        return e;
    }

    private Rental toDomain(RentalJpaEntity e) {
        var reg = new Rental(
                RentalId.of(e.id),
                new PropertyRef(e.propertyId),
                new OwnerRef(e.ownerId),
                new TenantRef(e.tenantId),
                new Rent(e.rent),
                new ExpiryDate(e.expiry)
        );
        if ("CANCELLED".equalsIgnoreCase(e.status)) {
            reg.cancel();
        }
        return reg;
    }
}
