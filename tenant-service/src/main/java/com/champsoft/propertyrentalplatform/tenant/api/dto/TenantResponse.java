package com.champsoft.propertyrentalplatform.tenant.api.dto;

import java.util.UUID;

public record TenantResponse(
        UUID id,
        String name,
        int score,
        String accountNumber,
        String ABA,
        String status) {}
