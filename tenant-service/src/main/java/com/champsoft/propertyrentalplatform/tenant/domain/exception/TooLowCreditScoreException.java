package com.champsoft.propertyrentalplatform.tenant.domain.exception;

public class TooLowCreditScoreException extends RuntimeException {
    public TooLowCreditScoreException(String message) {
        super(message);
    }
}
