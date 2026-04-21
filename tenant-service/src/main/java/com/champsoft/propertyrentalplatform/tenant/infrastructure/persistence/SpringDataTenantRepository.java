package com.champsoft.propertyrentalplatform.tenant.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTenantRepository extends JpaRepository<TenantJpaEntity, String> {
    boolean existsByNameIgnoreCase(String name);
}
