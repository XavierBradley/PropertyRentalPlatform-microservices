package com.champsoft.propertyrentalplatform.rental.application.service;

import com.champsoft.propertyrentalplatform.rental.application.exception.*;
import com.champsoft.propertyrentalplatform.rental.application.port.out.*;
import com.champsoft.propertyrentalplatform.rental.domain.model.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class RentalOrchestrator {

    private final PropertyEligibilityPort propertyPort;
    private final OwnerEligibilityPort ownerPort;
    private final TenantEligibilityPort tenantPort;
    private final RentalRepositoryPort repo;

    public RentalOrchestrator(
            PropertyEligibilityPort vehiclePort,
            OwnerEligibilityPort ownerPort,
            TenantEligibilityPort agentPort,
            RentalRepositoryPort repo
    ) {
        this.propertyPort = vehiclePort;
        this.ownerPort = ownerPort;
        this.tenantPort = agentPort;
        this.repo = repo;
    }

    @Transactional
    public Rental register(UUID vehicleId, UUID ownerId, UUID agentId, double rent, LocalDate expiry) {

        if (!propertyPort.isEligible(vehicleId)) {
            throw new CrossContextValidationException("Property is not eligible (must be AVAILABLE)");
        }
        if (!ownerPort.isEligible(ownerId)) {
            throw new CrossContextValidationException("Owner is not eligible");
        }
        if (!tenantPort.isEligible(agentId)) {
            throw new CrossContextValidationException("Tenant is not eligible");
        }

        var reg = new Rental(
                RentalId.newId(),
                new PropertyRef(vehicleId),
                new OwnerRef(ownerId),
                new TenantRef(agentId),
                new Rent(rent),
                new ExpiryDate(expiry)
        );

        return repo.save(reg);
    }
}
