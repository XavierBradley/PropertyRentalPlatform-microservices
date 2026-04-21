package com.champsoft.propertyrentalplatform.tenant.domain.model;

import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidAccountNumberException;
import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidABAException;


public record BankDetails(String accountNumber, String ABA) {
    public BankDetails {
        if (accountNumber == null || accountNumber.trim().isEmpty()) throw new IllegalArgumentException("account number is required");
        if (accountNumber.length() < 8 || accountNumber.length() > 12) throw new InvalidAccountNumberException("account number must have between 8 and 12 digits");

        if (ABA == null || ABA.trim().isEmpty()) throw new IllegalArgumentException("ABA number is required");
        if (ABA.length() != 8 ) throw new InvalidABAException("ABA must be 9 digits");
    }
}
