package com.champsoft.propertyrentalplatform.rental.infrastructure.persistence;

import com.champsoft.propertyrentalplatform.rental.domain.model.*;
import com.champsoft.propertyrentalplatform.rental.application.port.out.RentalRepositoryPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        e.id = UUID.fromString(reg.id().value());
        e.propertyId = UUID.fromString(reg.propertyId().value());
        e.ownerId = UUID.fromString(reg.ownerId().value());
        e.tenantId = UUID.fromString(reg.tenantId().value());
        e.rent = BigDecimal.valueOf(reg.rent().amount());
        e.expiry = reg.expiry().value();
        e.status = reg.status().name();
        return e;
    }

    private Rental toDomain(RentalJpaEntity e) {
        var reg = new Rental(
                RentalId.of(String.valueOf(e.id)),
                new PropertyRef(String.valueOf(e.propertyId)),
                new OwnerRef(String.valueOf(e.ownerId)),
                new TenantRef(String.valueOf(e.tenantId)),
                new Rent(Double.parseDouble(String.valueOf(e.rent))),
                new ExpiryDate(e.expiry)
        );
        if ("CANCELLED".equalsIgnoreCase(e.status)) {
            reg.cancel();
        }
        return reg;
    }
}
