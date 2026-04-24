package com.champsoft.propertyrentalplatform.rental.application.port.out;

import java.util.UUID;

public interface TenantEligibilityPort {
    boolean isEligible(UUID agentId);
}
