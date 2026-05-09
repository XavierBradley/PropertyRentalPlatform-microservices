package com.champsoft.propertyrentalplatform.tenant.infrastructure.persistence;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tenants")
public class TenantJpaEntity {
    @Id
    public UUID id;

    @Column(nullable = false)
    public String name;

    @Column(name = "score", nullable = false)
    public int score;

    @Embedded
    public BankDetailsEmbeddable details;

    @Column(nullable = false)
    public String status;
}
