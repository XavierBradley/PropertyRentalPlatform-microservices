package com.champsoft.propertyrentalplatform.tenant.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SpringDataTenantRepository extends JpaRepository<TenantJpaEntity, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
