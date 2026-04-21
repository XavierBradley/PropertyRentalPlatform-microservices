package com.champsoft.propertyrentalplatform.rental.application.port.out;

public interface TenantEligibilityPort {
    boolean isEligible(String agentId);
}
