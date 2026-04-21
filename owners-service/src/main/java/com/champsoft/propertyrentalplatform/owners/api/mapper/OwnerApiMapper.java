package com.champsoft.propertyrentalplatform.owners.api.mapper;

import com.champsoft.propertyrentalplatform.owners.api.dto.OwnerResponse;
import com.champsoft.propertyrentalplatform.owners.domain.model.Owner;

public class OwnerApiMapper {
    public static OwnerResponse toResponse(Owner o) {
        return new OwnerResponse(
                o.id().value(),
                o.fullName().value(),
                o.address().value(),
                o.status().name()
        );
    }
}
