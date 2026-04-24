package com.champsoft.propertyrentalplatform.tenant.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataTenantRepository extends JpaRepository<TenantJpaEntity, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
