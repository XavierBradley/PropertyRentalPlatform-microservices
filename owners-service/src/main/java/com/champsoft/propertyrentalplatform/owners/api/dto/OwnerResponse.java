package com.champsoft.propertyrentalplatform.owners.api.dto;

import java.util.UUID;

public record OwnerResponse(
        UUID id,
        String fullName,
        String address,
        String status) {}
