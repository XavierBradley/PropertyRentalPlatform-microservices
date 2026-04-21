package com.champsoft.propertyrentalplatform.tenant.api.dto;

public record TenantResponse(
        String id,
        String name,
        int role,
        String accountNumber,
        String ABA,
        String status) {}
