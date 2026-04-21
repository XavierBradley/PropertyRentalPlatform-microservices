package com.champsoft.propertyrentalplatform.rental.infrastructure.acl;

import com.champsoft.propertyrentalplatform.tenant.application.service.TenantEligibilityService;
import com.champsoft.propertyrentalplatform.rental.application.port.out.TenantEligibilityPort;
import org.springframework.stereotype.Component;

@Component
public class TenantEligibilityRestAdapter implements TenantEligibilityPort {

    private final TenantEligibilityService tenantEligibility;

    public TenantEligibilityRestAdapter(TenantEligibilityService tenantEligibility) {
        this.tenantEligibility = tenantEligibility;
    }

    @Override
    public boolean isEligible(String tenantId) {
        return tenantEligibility.isEligible(tenantId);
    }
}
