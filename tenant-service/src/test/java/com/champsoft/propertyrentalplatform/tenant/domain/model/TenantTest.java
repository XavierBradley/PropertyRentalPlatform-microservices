package com.champsoft.propertyrentalplatform.tenant.domain.model;

import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidTenantNameException;
import com.champsoft.propertyrentalplatform.tenant.domain.exception.TooLowCreditScoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TenantTest {

    private Tenant tenant() {

        return new Tenant(
                TenantId.newId(),
                "John Doe",
                new CreditScore(700),
                new BankDetails(
                        "123456789012",
                        "123456789"
                )
        );
    }

    @Test
    @DisplayName("Should create inactive tenant")
    void shouldCreateInactiveTenant() {

        Tenant tenant = tenant();

        assertThat(tenant.name())
                .isEqualTo("John Doe");

        assertThat(tenant.score().value())
                .isEqualTo(700);

        assertThat(tenant.status())
                .isEqualTo(TenantStatus.INACTIVE);
    }

    @Test
    @DisplayName("Should trim tenant name")
    void shouldTrimTenantName() {

        Tenant tenant = new Tenant(
                TenantId.newId(),
                "  John Doe  ",
                new CreditScore(700),
                new BankDetails(
                        "123456789012",
                        "123456789"
                )
        );

        assertThat(tenant.name())
                .isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Should update tenant")
    void shouldUpdateTenant() {

        Tenant tenant = tenant();

        tenant.update(
                "Jane Doe",
                new CreditScore(750),
                new BankDetails(
                        "999999999999",
                        "987654321"
                )
        );

        assertThat(tenant.name())
                .isEqualTo("Jane Doe");

        assertThat(tenant.score().value())
                .isEqualTo(750);

        assertThat(tenant.details().accountNumber())
                .isEqualTo("999999999999");
    }

    @Test
    @DisplayName("Should activate tenant")
    void shouldActivateTenant() {

        Tenant tenant = tenant();

        tenant.activate();

        assertThat(tenant.status())
                .isEqualTo(TenantStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should be eligible when active")
    void shouldBeEligibleWhenActive() {

        Tenant tenant = tenant();

        tenant.activate();

        assertThat(tenant.isEligibleForRegistration())
                .isTrue();
    }

    @Test
    @DisplayName("Should not be eligible when inactive")
    void shouldNotBeEligibleWhenInactive() {

        Tenant tenant = tenant();

        assertThat(tenant.isEligibleForRegistration())
                .isFalse();
    }

    @Test
    @DisplayName("Should throw when tenant name contains invalid characters")
    void shouldThrowWhenTenantNameContainsInvalidCharacters() {

        assertThrows(
                InvalidTenantNameException.class,
                () -> new Tenant(
                        TenantId.newId(),
                        "John123",
                        new CreditScore(700),
                        new BankDetails(
                                "123456789012",
                                "123456789"
                        )
                )
        );
    }

    @Test
    @DisplayName("Should throw when tenant name is too short")
    void shouldThrowWhenTenantNameIsTooShort() {

        assertThrows(
                InvalidTenantNameException.class,
                () -> new Tenant(
                        TenantId.newId(),
                        "J",
                        new CreditScore(700),
                        new BankDetails(
                                "123456789012",
                                "123456789"
                        )
                )
        );
    }

    @Test
    @DisplayName("Should throw when tenant name is too long")
    void shouldThrowWhenTenantNameIsTooLong() {

        String longName = "A".repeat(101);

        assertThrows(
                InvalidTenantNameException.class,
                () -> new Tenant(
                        TenantId.newId(),
                        longName,
                        new CreditScore(700),
                        new BankDetails(
                                "123456789012",
                                "123456789"
                        )
                )
        );
    }

    @Test
    @DisplayName("Should throw when credit score is too low for tenant")
    void shouldThrowWhenCreditScoreIsTooLowForTenant() {

        assertThrows(
                TooLowCreditScoreException.class,
                () -> new Tenant(
                        TenantId.newId(),
                        "John Doe",
                        new CreditScore(350),
                        new BankDetails(
                                "123456789012",
                                "123456789"
                        )
                )
        );
    }
}