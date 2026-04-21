package com.champsoft.propertyrentalplatform.rental.infrastructure.acl;

import com.champsoft.propertyrentalplatform.property.application.service.PropertyEligibilityService;
import com.champsoft.propertyrentalplatform.rental.application.port.out.PropertyEligibilityPort;
import org.springframework.stereotype.Component;

@Component
public class PropertyEligibilityRestAdapter implements PropertyEligibilityPort {

    private final PropertyEligibilityService propertyEligibility;

    public PropertyEligibilityRestAdapter(PropertyEligibilityService propertyEligibility) {
        this.propertyEligibility = propertyEligibility;
    }

    @Override
    public boolean isEligible(String propertyId) {
        return propertyEligibility.isEligible(propertyId);
    }
}
