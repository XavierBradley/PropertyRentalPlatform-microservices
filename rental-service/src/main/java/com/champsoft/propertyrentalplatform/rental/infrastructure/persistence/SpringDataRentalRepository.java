package com.champsoft.propertyrentalplatform.rental.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataRentalRepository extends JpaRepository<RentalJpaEntity, UUID> {
}
