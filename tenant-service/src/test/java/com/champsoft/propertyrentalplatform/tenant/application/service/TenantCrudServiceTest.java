package com.champsoft.propertyrentalplatform.tenant.application.service;

import com.champsoft.propertyrentalplatform.tenant.application.exception.DuplicateTenantException;
import com.champsoft.propertyrentalplatform.tenant.application.exception.TenantNotFoundException;
import com.champsoft.propertyrentalplatform.tenant.application.port.out.TenantRepositoryPort;
import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidTenantNameException;
import com.champsoft.propertyrentalplatform.tenant.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TenantCrudServiceTest {

    private TenantRepositoryPort repo;

    private TenantCrudService service;

    @BeforeEach
    void setUp() {

        repo = mock(TenantRepositoryPort.class);

        service = new TenantCrudService(repo);
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
    @DisplayName("Should create tenant")
    void shouldCreateTenant() {

        when(repo.existsByName("John Doe"))
                .thenReturn(false);

        when(repo.save(any(Tenant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Tenant result = service.create(
                "John Doe",
                700,
                "123456789012",
                "123456789"
        );

        assertThat(result.name())
                .isEqualTo("John Doe");

        assertThat(result.score().value())
                .isEqualTo(700);

        assertThat(result.status())
                .isEqualTo(TenantStatus.INACTIVE);

        verify(repo).save(any(Tenant.class));
    }

    @Test
    @DisplayName("Should throw when tenant already exists")
    void shouldThrowWhenTenantAlreadyExists() {

        when(repo.existsByName("John Doe"))
                .thenReturn(true);

        assertThrows(
                DuplicateTenantException.class,
                () -> service.create(
                        "John Doe",
                        700,
                        "123456789012",
                        "123456789"
                )
        );

        verify(repo, never()).save(any());
    }

    @Test
    @DisplayName("Should throw when tenant name is invalid")
    void shouldThrowWhenTenantNameIsInvalid() {

        when(repo.existsByName(any()))
                .thenReturn(false);

        assertThrows(
                InvalidTenantNameException.class,
                () -> service.create(
                        "John123",
                        700,
                        "123456789012",
                        "123456789"
                )
        );
    }

    @Test
    @DisplayName("Should get tenant by id")
    void shouldGetTenantById() {

        UUID id = UUID.randomUUID();

        Tenant tenant = tenant(id);

        when(repo.findById(TenantId.of(id)))
                .thenReturn(Optional.of(tenant));

        Tenant result = service.getById(id);

        assertThat(result).isEqualTo(tenant);
    }

    @Test
    @DisplayName("Should throw when tenant not found")
    void shouldThrowWhenTenantNotFound() {

        UUID id = UUID.randomUUID();

        when(repo.findById(TenantId.of(id)))
                .thenReturn(Optional.empty());

        assertThrows(
                TenantNotFoundException.class,
                () -> service.getById(id)
        );
    }

    @Test
    @DisplayName("Should list tenants")
    void shouldListTenants() {

        List<Tenant> tenants = List.of(
                tenant(UUID.randomUUID()),
                new Tenant(
                        TenantId.of(UUID.randomUUID()),
                        "Jane Doe",
                        new CreditScore(750),
                        new BankDetails(
                                "999999999999",
                                "987654321"
                        )
                )
        );

        when(repo.findAll())
                .thenReturn(tenants);

        List<Tenant> result = service.list();

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("Should update tenant")
    void shouldUpdateTenant() {

        UUID id = UUID.randomUUID();

        Tenant tenant = tenant(id);

        when(repo.findById(TenantId.of(id)))
                .thenReturn(Optional.of(tenant));

        when(repo.save(any(Tenant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Tenant updated = service.update(
                id,
                "Jane Doe",
                750,
                "999999999999",
                "987654321"
        );

        assertThat(updated.name())
                .isEqualTo("Jane Doe");

        assertThat(updated.score().value())
                .isEqualTo(750);

        assertThat(updated.details().accountNumber())
                .isEqualTo("999999999999");
    }

    @Test
    @DisplayName("Should activate tenant")
    void shouldActivateTenant() {

        UUID id = UUID.randomUUID();

        Tenant tenant = tenant(id);

        when(repo.findById(TenantId.of(id)))
                .thenReturn(Optional.of(tenant));

        when(repo.save(any(Tenant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Tenant activated = service.activate(id);

        assertThat(activated.status())
                .isEqualTo(TenantStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should delete tenant")
    void shouldDeleteTenant() {

        UUID id = UUID.randomUUID();

        Tenant tenant = tenant(id);

        when(repo.findById(TenantId.of(id)))
                .thenReturn(Optional.of(tenant));

        service.delete(id);

        verify(repo).deleteById(TenantId.of(id));
    }
}