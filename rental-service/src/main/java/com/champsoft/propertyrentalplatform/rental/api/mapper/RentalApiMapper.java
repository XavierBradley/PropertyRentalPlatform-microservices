package com.champsoft.propertyrentalplatform.rental.api.mapper;

import com.champsoft.propertyrentalplatform.rental.api.dto.RentalResponse;
import com.champsoft.propertyrentalplatform.rental.domain.model.Rental;

public class RentalApiMapper {
    public static RentalResponse toResponse(Rental r) {
        return new RentalResponse(
                r.id().value(),
                r.propertyIdValue(),
                r.ownerIdValue(),
                r.tenantIdValue(),
                r.rent().amount(),
                r.expiryValue(),
                r.status().name()
        );
    }
}
