package com.champsoft.propertyrentalplatform.property.domain.exception;

public class PropertyAlreadyBeingRentedException extends RuntimeException {
    public PropertyAlreadyBeingRentedException(String message) { super(message); }
}
