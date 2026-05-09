package com.champsoft.propertyrentalplatform.tenant.application.service;

import com.champsoft.propertyrentalplatform.tenant.application.exception.TenantNotFoundException;
import com.champsoft.propertyrentalplatform.tenant.application.port.out.TenantRepositoryPort;
import com.champsoft.propertyrentalplatform.tenant.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TenantEligibilityServiceTest {

    private TenantRepositoryPort repo;

    private TenantEligibilityService service;

    @BeforeEach
    void setUp() {

        repo = mock(TenantRepositoryPort.class);

        service = new TenantEligibilityService(repo);
    }

    private Tenant tenant(UUID id) {

        return new Tenant(
                TenantId.of(id),
                "John Doe",
                new CreditScore(700),
                new BankDetails(
                        "123456789012",
                        "123456789"
                )
        );
    }

    @Test
    @DisplayName("Should return false when tenant is inactive")
    void shouldReturnFalseWhenTenantIsInactive() {

        UUID id = UUID.randomUUID();

        Tenant tenant = tenant(id);

        when(repo.findById(TenantId.of(id)))
                .thenReturn(Optional.of(tenant));

        boolean result = service.isEligible(id);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return true when tenant is active")
    void shouldReturnTrueWhenTenantIsActive() {

        UUID id = UUID.randomUUID();

        Tenant tenant = tenant(id);

        tenant.activate();

        when(repo.findById(TenantId.of(id)))
                .thenReturn(Optional.of(tenant));

        boolean result = service.isEligible(id);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should throw when tenant does not exist")
    void shouldThrowWhenTenantDoesNotExist() {

        UUID id = UUID.randomUUID();

        when(repo.findById(TenantId.of(id)))
                .thenReturn(Optional.empty());

        assertThrows(
                TenantNotFoundException.class,
                () -> service.isEligible(id)
        );
    }
}