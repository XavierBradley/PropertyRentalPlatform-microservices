package com.champsoft.propertyrentalplatform.rental.infrastructure.acl;

//import com.champsoft.propertyrentalplatform.owners.application.service.OwnerEligibilityService;
import com.champsoft.propertyrentalplatform.rental.application.port.out.OwnerEligibilityPort;
import org.springframework.stereotype.Component;

@Component
public class OwnerEligibilityRestAdapter implements OwnerEligibilityPort {

    private final OwnerEligibilityService ownersEligibility;

    public OwnerEligibilityRestAdapter(OwnerEligibilityService ownersEligibility) {
        this.ownersEligibility = ownersEligibility;
    }

    @Override
    public boolean isEligible(String ownerId) {
        return ownersEligibility.isEligible(ownerId);
    }
}
