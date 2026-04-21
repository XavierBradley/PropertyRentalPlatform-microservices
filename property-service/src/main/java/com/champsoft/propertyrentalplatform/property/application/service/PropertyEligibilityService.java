package com.champsoft.propertyrentalplatform.property.application.service;

import com.champsoft.propertyrentalplatform.property.application.exception.PropertyNotFoundException;
import com.champsoft.propertyrentalplatform.property.application.port.out.PropertyRepositoryPort;
import com.champsoft.propertyrentalplatform.property.domain.model.PropertyId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropertyEligibilityService {

    private final PropertyRepositoryPort repo;

    public PropertyEligibilityService(PropertyRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public boolean isEligible(String vehicleId) {
        return repo.findById(PropertyId.of(vehicleId))
                .map(v -> v.isEligibleToBeRented())
                .orElseThrow(() -> new PropertyNotFoundException("Vehicle not found: " + vehicleId));
    }
}
