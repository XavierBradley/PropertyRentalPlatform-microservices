package com.champsoft.propertyrentalplatform.rental.application.port.out;

public interface PropertyEligibilityPort {
    boolean isEligible(String vehicleId);
}
