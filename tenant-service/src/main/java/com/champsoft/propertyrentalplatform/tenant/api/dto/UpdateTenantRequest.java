package com.champsoft.propertyrentalplatform.tenant.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateTenantRequest(
        @NotBlank String name,
        int score,
        @NotBlank String accountNumber,
        @NotBlank String ABA) {}

