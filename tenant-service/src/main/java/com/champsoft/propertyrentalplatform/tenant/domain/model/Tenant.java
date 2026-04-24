package com.champsoft.propertyrentalplatform.tenant.domain.model;

import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidTenantNameException;
import com.champsoft.propertyrentalplatform.tenant.domain.exception.TooLowCreditScoreException;

public class Tenant {
    private final TenantId id;
    private String name;
    private CreditScore score;
    private BankDetails details;
    private TenantStatus status;

    public Tenant(TenantId id, String name, CreditScore score, BankDetails details) {
        this.id = id;
        setName(name);
        setCreditScore(score);
        this.details = details;
        this.status = TenantStatus.INACTIVE;
    }

    public TenantId id() { return id; }
    public String name() { return name; }
    public CreditScore score() { return score; }
    public TenantStatus status() { return status; }
    public BankDetails details() { return details; }

    public void update(String name, CreditScore score, BankDetails details) {
        setName(name);
        setCreditScore(score);
        this.details = details;
    }

    public void activate() { this.status = TenantStatus.ACTIVE; }

    public boolean isEligibleForRegistration() { return status == TenantStatus.ACTIVE; }

    private void setName(String name) {
        if (!name.matches("[A-Za-z -]+")) {
            throw new InvalidTenantNameException("Name must contain only letters");
        }

        String v = name.trim();
        if (v.length() < 2 || v.length() > 100) throw new InvalidTenantNameException("Tenant name length must be 2..100");
        this.name = v;
    }

    private void setCreditScore(CreditScore score) {
        if (score.value() < 400) throw new TooLowCreditScoreException("Tenant needs a credit score of at leat 400 to rent a property");
        this.score = score;
    }
}
