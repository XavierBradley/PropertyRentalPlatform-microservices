package com.champsoft.propertyrentalplatform.tenant.infrastructure.persistence;

import com.champsoft.propertyrentalplatform.tenant.application.port.out.TenantRepositoryPort;
import com.champsoft.propertyrentalplatform.tenant.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaTenantRepositoryAdapter implements TenantRepositoryPort {

    private final SpringDataTenantRepository jpa;

    public JpaTenantRepositoryAdapter(SpringDataTenantRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Tenant save(Tenant tenant) {
        TenantJpaEntity saved = jpa.save(toEntity(tenant));
        return toDomain(saved);
    }

    @Override
    public Optional<Tenant> findById(TenantId id) {
        return jpa.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByNameIgnoreCase(name.trim());
    }

    @Override
    public List<Tenant> findAll() {
        return jpa.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(TenantId id) {
        jpa.deleteById(id.value());
    }

    private TenantJpaEntity toEntity(Tenant t) {
        TenantJpaEntity e = new TenantJpaEntity();
        e.id = t.id().value();
        e.name = t.name();
        e.score = t.score().value();
        e.details = new BankDetailsEmbeddable(
                t.details().accountNumber(),
                t.details().ABA()
        );
        e.status = t.status().name();
        return e;
    }

    private Tenant toDomain(TenantJpaEntity e) {
        Tenant tenant = new Tenant(
                TenantId.of(e.id),
                e.name,
                new CreditScore(e.score),
                new BankDetails(
                        e.details.accountNumber,
                        e.details.ABA
                )
        );

        // restore status correctly (THIS FIXES YOUR TEST FAILURES)
        if ("ACTIVE".equalsIgnoreCase(e.status)) {
            tenant.activate();
        }

        return tenant;
    }
}