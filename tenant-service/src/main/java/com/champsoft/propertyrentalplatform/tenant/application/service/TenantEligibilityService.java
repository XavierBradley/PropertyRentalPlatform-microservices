package com.champsoft.propertyrentalplatform.tenant.application.service;

import com.champsoft.propertyrentalplatform.tenant.application.exception.TenantNotFoundException;
import com.champsoft.propertyrentalplatform.tenant.application.port.out.TenantRepositoryPort;
import com.champsoft.propertyrentalplatform.tenant.domain.model.TenantId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantEligibilityService {

    private final TenantRepositoryPort repo;
    public TenantEligibilityService(TenantRepositoryPort repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public boolean isEligible(String agentId) {
        return repo.findById(TenantId.of(agentId))
                .map(t -> t.isEligibleForRegistration())
                .orElseThrow(() -> new TenantNotFoundException("Agent not found: " + agentId));
    }
}
