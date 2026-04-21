package com.champsoft.propertyrentalplatform.tenant.application.exception;

public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException(String message) { super(message); }
}
