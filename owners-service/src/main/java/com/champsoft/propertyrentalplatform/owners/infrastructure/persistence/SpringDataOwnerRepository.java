package com.champsoft.propertyrentalplatform.owners.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataOwnerRepository extends JpaRepository<OwnerJpaEntity, UUID> {
    boolean existsByFullNameIgnoreCase(String fullName);
}
