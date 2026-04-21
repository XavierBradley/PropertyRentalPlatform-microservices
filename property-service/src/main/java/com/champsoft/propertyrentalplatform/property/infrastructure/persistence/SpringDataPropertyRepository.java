package com.champsoft.propertyrentalplatform.property.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataPropertyRepository extends JpaRepository<PropertyJpaEntity, String> {
    Optional<PropertyJpaEntity> findByAddress(String address);
    boolean existsByAddress(String address);
}
