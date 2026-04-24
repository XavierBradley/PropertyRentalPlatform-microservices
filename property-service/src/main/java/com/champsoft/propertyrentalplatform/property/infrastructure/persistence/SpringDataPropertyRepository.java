package com.champsoft.propertyrentalplatform.property.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataPropertyRepository extends JpaRepository<PropertyJpaEntity, UUID> {
    Optional<PropertyJpaEntity> findByAddress(String address);
    boolean existsByAddress(String address);
}
