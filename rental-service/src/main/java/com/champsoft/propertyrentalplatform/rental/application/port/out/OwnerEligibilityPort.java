package com.champsoft.propertyrentalplatform.rental.application.port.out;

import java.util.UUID;

public interface OwnerEligibilityPort {
    boolean isEligible(UUID ownerId);
}
