package com.champsoft.propertyrentalplatform.rental.application.port.out;

import java.util.UUID;

public interface PropertyEligibilityPort {
    boolean isEligible(UUID vehicleId);
}
