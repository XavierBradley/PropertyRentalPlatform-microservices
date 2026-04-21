package com.champsoft.propertyrentalplatform.tenant.domain.exception;

public class TenantNotEligibleException extends RuntimeException {
    public TenantNotEligibleException(String message) { super(message); }
}
