package com.champsoft.propertyrentalplatform.rental.application.port.out;

public interface OwnerEligibilityPort {
    boolean isEligible(String ownerId);
}
