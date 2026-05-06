package com.champsoft.propertyrentalplatform.tenant.domain.model;

import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidABAException;
import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidAccountNumberException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Domain test → validation rules only
class BankDetailsTest {

    @Test
    void shouldCreateValidBankDetails() {

        // ------------------- Act -------------------
        BankDetails details = new BankDetails("123456789012", "123456789");

        // ------------------- Assert -------------------
        assertThat(details.accountNumber()).isEqualTo("123456789012");
        assertThat(details.ABA()).isEqualTo("123456789");
    }

    @Test
    void shouldRejectInvalidAccountNumberLength() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() ->
                new BankDetails("123", "123456789")
        ).isInstanceOf(InvalidAccountNumberException.class);
    }

    @Test
    void shouldRejectInvalidABALength() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() ->
                new BankDetails("123456789012", "123")
        ).isInstanceOf(InvalidABAException.class);
    }

    @Test
    void shouldRejectBlankValues() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() ->
                new BankDetails("", "123456789")
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() ->
                new BankDetails("123456789012", "")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}