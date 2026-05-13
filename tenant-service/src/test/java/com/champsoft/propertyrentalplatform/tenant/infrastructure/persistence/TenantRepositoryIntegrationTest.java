package com.champsoft.propertyrentalplatform.tenant.infrastructure.persistence;

import com.champsoft.propertyrentalplatform.tenant.domain.model.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaTenantRepositoryAdapter.class)
@Transactional
class TenantRepositoryIntegrationTest {

    @Autowired
    private JpaTenantRepositoryAdapter adapter;

    @Autowired
    private SpringDataTenantRepository jpa;

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
    @DisplayName("Should save tenant")
    void shouldSaveTenant() {

        UUID id = UUID.randomUUID();

        Tenant tenant = tenant(id);

        adapter.save(tenant);

        Optional<TenantJpaEntity> saved =
                jpa.findById(id);

        assertThat(saved).isPresent();

        assertThat(saved.get().id)
                .isEqualTo(id);

        assertThat(saved.get().name)
                .isEqualTo("John Doe");

        assertThat(saved.get().score)
                .isEqualTo(700);

        assertThat(saved.get().status)
                .isEqualTo("INACTIVE");

        assertThat(saved.get().details.accountNumber)
                .isEqualTo("123456789012");

        assertThat(saved.get().details.ABA)
                .isEqualTo("123456789");
    }

    @Test
    @DisplayName("Should find tenant by id")
    void shouldFindTenantById() {

        UUID id = UUID.randomUUID();

        Tenant tenant = tenant(id);

        adapter.save(tenant);

        Optional<Tenant> found =
                adapter.findById(TenantId.of(id));

        assertThat(found).isPresent();

        assertThat(found.get().id().value())
                .isEqualTo(id);

        assertThat(found.get().name())
                .isEqualTo("John Doe");

        assertThat(found.get().status())
                .isEqualTo(TenantStatus.INACTIVE);
    }

    @Test
    @DisplayName("Should return empty when tenant does not exist")
    void shouldReturnEmptyWhenTenantDoesNotExist() {

        Optional<Tenant> result =
                adapter.findById(
                        TenantId.of(UUID.randomUUID())
                );

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should detect existing tenant name")
    void shouldDetectExistingTenantName() {

        Tenant tenant =
                tenant(UUID.randomUUID());

        adapter.save(tenant);

        boolean exists =
                adapter.existsByName("John Doe");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should ignore case when checking tenant name")
    void shouldIgnoreCaseWhenCheckingTenantName() {

        Tenant tenant =
                tenant(UUID.randomUUID());

        adapter.save(tenant);

        boolean exists =
                adapter.existsByName("john doe");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when tenant name does not exist")
    void shouldReturnFalseWhenTenantNameDoesNotExist() {

        boolean exists =
                adapter.existsByName("Unknown");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should return all tenants")
    void shouldReturnAllTenants() {

        Tenant tenant1 =
                tenant(UUID.randomUUID());

        Tenant tenant2 = new Tenant(
                TenantId.of(UUID.randomUUID()),
                "Jane Doe",
                new CreditScore(750),
                new BankDetails(
                        "999999999999",
                        "987654321"
                )
        );

        adapter.save(tenant1);
        adapter.save(tenant2);

        List<Tenant> tenants =
                adapter.findAll();

        assertThat(tenants).hasSize(2);
    }

    @Test
    @DisplayName("Should delete tenant")
    void shouldDeleteTenant() {

        UUID id = UUID.randomUUID();

        Tenant tenant = tenant(id);

        adapter.save(tenant);

        adapter.deleteById(TenantId.of(id));

        Optional<TenantJpaEntity> deleted =
                jpa.findById(id);

        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("Should restore active tenant from database")
    void shouldRestoreActiveTenantFromDatabase() {

        UUID id = UUID.randomUUID();

        Tenant tenant = tenant(id);

        tenant.activate();

        adapter.save(tenant);

        Optional<Tenant> restored =
                adapter.findById(TenantId.of(id));

        assertThat(restored).isPresent();

        assertThat(restored.get().status())
                .isEqualTo(TenantStatus.ACTIVE);
    }
}