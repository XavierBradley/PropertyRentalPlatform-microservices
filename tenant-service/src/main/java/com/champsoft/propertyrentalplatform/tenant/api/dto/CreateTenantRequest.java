package com.champsoft.propertyrentalplatform.tenant.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTenantRequest(
        @NotBlank String name,
        int score,
        @NotBlank String accountNumber,
        @NotBlank String ABA) {}
