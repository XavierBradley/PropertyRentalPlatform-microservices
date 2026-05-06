package com.champsoft.propertyrentalplatform.tenant.domain.model;

import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidTenantNameException;
import com.champsoft.propertyrentalplatform.tenant.domain.exception.TooLowCreditScoreException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Domain test → full aggregate behavior
class TenantTest {

    private Tenant validTenant() {
        return new Tenant(
                TenantId.newId(),
                "John Doe",
                new CreditScore(700),
                new BankDetails("123456789012", "123456789")
        );
    }

    @Test
    void shouldCreateInactiveTenant() {

        // ------------------- Arrange -------------------
        Tenant tenant = validTenant();

        // ------------------- Assert -------------------
        assertThat(tenant.status()).isEqualTo(TenantStatus.INACTIVE);
        assertThat(tenant.isEligibleForRegistration()).isFalse();
    }

    @Test
    void shouldActivateTenant() {

        // ------------------- Arrange -------------------
        Tenant tenant = validTenant();

        // ------------------- Act -------------------
        tenant.activate();

        // ------------------- Assert -------------------
        assertThat(tenant.status()).isEqualTo(TenantStatus.ACTIVE);
        assertThat(tenant.isEligibleForRegistration()).isTrue();
    }

    @Test
    void shouldRejectInvalidTenantName() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() ->
                new Tenant(
                        TenantId.newId(),
                        "12345",
                        new CreditScore(700),
                        new BankDetails("123456789012", "123456789")
                )
        ).isInstanceOf(InvalidTenantNameException.class);
    }

    @Test
    void shouldRejectLowCreditScoreOnCreation() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() ->
                new Tenant(
                        TenantId.newId(),
                        "John Doe",
                        new CreditScore(350),
                        new BankDetails("123456789012", "123456789")
                )
        ).isInstanceOf(TooLowCreditScoreException.class);
    }

    @Test
    void shouldUpdateTenantSuccessfully() {

        // ------------------- Arrange -------------------
        Tenant tenant = validTenant();

        // ------------------- Act -------------------
        tenant.update(
                "Jane Doe",
                new CreditScore(750),
                new BankDetails("999999999999", "111111111")
        );

        // ------------------- Assert -------------------
        assertThat(tenant.name()).isEqualTo("Jane Doe");
        assertThat(tenant.score().value()).isEqualTo(750);
        assertThat(tenant.details().accountNumber()).isEqualTo("999999999999");
    }
}