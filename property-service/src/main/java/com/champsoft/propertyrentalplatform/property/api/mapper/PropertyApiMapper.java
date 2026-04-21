package com.champsoft.propertyrentalplatform.property.api.mapper;

import com.champsoft.propertyrentalplatform.property.api.dto.PropertyResponse;
import com.champsoft.propertyrentalplatform.property.domain.model.Property;

public class PropertyApiMapper {
    public static PropertyResponse toResponse(Property p) {
        return new PropertyResponse(
                p.id().value(),
                p.tax().value(),
                p.address().value(),
                p.status().name()
        );
    }
}
