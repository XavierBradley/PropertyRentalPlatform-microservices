package com.champsoft.propertyrentalplatform.owners.infrastructure.persistence;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "owners")
public class OwnerJpaEntity {
    @Id
    public UUID id;

    @Column(name = "full_name", nullable = false)
    public String fullName;

    @Column(name = "address")
    public String address;

    @Column(nullable = false)
    public String status;
}
