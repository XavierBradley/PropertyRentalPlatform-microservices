package com.champsoft.propertyrentalplatform.tenant.infrastructure.persistence;

import com.champsoft.propertyrentalplatform.tenant.application.port.out.TenantRepositoryPort;
import com.champsoft.propertyrentalplatform.tenant.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaTenantRepositoryAdapter implements TenantRepositoryPort {

    private final SpringDataTenantRepository jpa;
    public JpaTenantRepositoryAdapter(SpringDataTenantRepository jpa) { this.jpa = jpa; }

    @Override
    public Tenant save(Tenant tenant) {
        jpa.save(toEntity(tenant));
        return tenant;
    }

    @Override
    public Optional<Tenant> findById(TenantId id) {
        return jpa.findById(id.value()).map(this::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByNameIgnoreCase(name);
    }

    @Override
    public List<Tenant> findAll() { return jpa.findAll().stream().map(this::toDomain).toList(); }

    @Override
    public void deleteById(TenantId id) { jpa.deleteById(id.value()); }

    private TenantJpaEntity toEntity(Tenant a) {
        var e = new TenantJpaEntity();
        e.id = a.id().value();
        e.name = a.name();
        e.score = a.score().value();
        e.details = new BankDetailsEmbeddable(
                a.details().accountNumber(),
                a.details().ABA()
        );
        e.status = a.status().name();
        return e;
    }

    private Tenant toDomain(TenantJpaEntity e) {
        var a = new Tenant(
                TenantId.of(e.id),
                e.name,
                new CreditScore(e.score),
                new BankDetails(
                        e.details.accountNumber,
                        e.details.ABA
                )
        );
        if ("ACTIVE".equalsIgnoreCase(e.status)) a.activate();
        return a;
    }
}
