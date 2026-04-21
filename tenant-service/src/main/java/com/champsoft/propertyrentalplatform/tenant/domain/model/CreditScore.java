package com.champsoft.propertyrentalplatform.tenant.domain.model;

import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidCreditScoreException;

public final class CreditScore {
    private final int value;

    public CreditScore(int value) {
        if (value < 300) throw new InvalidCreditScoreException("Credit Score cannot be less than 300");
        this.value = value;
    }

    public int value() { return value; }
}