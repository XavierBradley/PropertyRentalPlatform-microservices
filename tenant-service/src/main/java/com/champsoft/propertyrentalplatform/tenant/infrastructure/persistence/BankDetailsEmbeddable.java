package com.champsoft.propertyrentalplatform.tenant.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class BankDetailsEmbeddable {
    @Column(name = "account_number", nullable = false)
    public String accountNumber;

    @Column(name = "ABA", nullable = false)
    public String ABA;

    protected BankDetailsEmbeddable() {
    }

    public BankDetailsEmbeddable(String accountNumber, String ABA) {
        this.accountNumber = accountNumber;
        this.ABA = ABA;
    }
}
