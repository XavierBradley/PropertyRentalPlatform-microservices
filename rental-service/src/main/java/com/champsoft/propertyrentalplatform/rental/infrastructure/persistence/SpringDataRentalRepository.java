package com.champsoft.propertyrentalplatform.rental.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface SpringDataRentalRepository extends JpaRepository<RentalJpaEntity, UUID> {
}
