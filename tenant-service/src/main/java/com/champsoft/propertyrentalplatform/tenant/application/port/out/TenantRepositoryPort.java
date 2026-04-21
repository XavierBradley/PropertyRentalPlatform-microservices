package com.champsoft.propertyrentalplatform.tenant.application.port.out;

import com.champsoft.propertyrentalplatform.tenant.domain.model.Tenant;
import com.champsoft.propertyrentalplatform.tenant.domain.model.TenantId;

import java.util.List;
import java.util.Optional;

public interface TenantRepositoryPort {
    Tenant save(Tenant tenant);
    Optional<Tenant> findById(TenantId id);
    boolean existsByName(String name);
    List<Tenant> findAll();
    void deleteById(TenantId id);
}
