package com.champsoft.propertyrentalplatform.owners.application.service;

import com.champsoft.propertyrentalplatform.owners.application.exception.OwnerNotFoundException;
import com.champsoft.propertyrentalplatform.owners.application.port.out.OwnerRepositoryPort;
import com.champsoft.propertyrentalplatform.owners.domain.model.OwnerId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OwnerEligibilityService {

    private final OwnerRepositoryPort repo;
    public OwnerEligibilityService(OwnerRepositoryPort repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public boolean isEligible(UUID ownerId) {
        return repo.findById(OwnerId.of(ownerId))
                .map(o -> o.isEligibleForRegistration())
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found: " + ownerId));
    }
}
