package com.champsoft.propertyrentalplatform.tenant.api.mapper;

import com.champsoft.propertyrentalplatform.tenant.api.dto.TenantResponse;
import com.champsoft.propertyrentalplatform.tenant.domain.model.Tenant;

public class TenantApiMapper {
    public static TenantResponse toResponse(Tenant t) {
        return new TenantResponse(
                t.id().value(),
                t.name(),
                t.score().value(),
                t.details().accountNumber(),
                t.details().ABA(),
                t.status().name()
        );
    }
}
