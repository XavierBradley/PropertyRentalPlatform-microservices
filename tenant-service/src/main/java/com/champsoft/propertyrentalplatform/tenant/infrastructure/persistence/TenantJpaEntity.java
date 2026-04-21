package com.champsoft.propertyrentalplatform.tenant.infrastructure.persistence;

import com.champsoft.propertyrentalplatform.tenant.domain.model.CreditScore;
import jakarta.persistence.*;

@Entity
@Table(name = "tenants")
public class TenantJpaEntity {
    @Id
    public String id;

    @Column(nullable = false)
    public String name;

    @Column(name = "score", nullable = false)
    public int score;

    @Embedded
    public BankDetailsEmbeddable details;

    @Column(nullable = false)
    public String status;
}
