package com.champsoft.propertyrentalplatform.property.domain.exception;

public class PropertyAlreadyActiveException extends RuntimeException {
    public PropertyAlreadyActiveException(String message) { super(message); }
}
