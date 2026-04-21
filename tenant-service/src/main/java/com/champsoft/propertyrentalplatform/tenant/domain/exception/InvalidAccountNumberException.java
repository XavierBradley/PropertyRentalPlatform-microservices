package com.champsoft.propertyrentalplatform.tenant.domain.exception;

public class InvalidAccountNumberException extends RuntimeException {
  public InvalidAccountNumberException(String message) {
    super(message);
  }
}
