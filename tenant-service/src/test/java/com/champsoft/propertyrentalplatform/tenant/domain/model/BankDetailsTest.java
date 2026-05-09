package com.champsoft.propertyrentalplatform.tenant.domain.model;

import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidABAException;
import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidAccountNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankDetailsTest {

    @Test
    @DisplayName("Should create valid bank details")
    void shouldCreateValidBankDetails() {

        BankDetails details =
                new BankDetails(
                        "123456789012",
                        "123456789"
                );

        assertThat(details.accountNumber())
                .isEqualTo("123456789012");

        assertThat(details.ABA())
                .isEqualTo("123456789");
    }

    @Test
    @DisplayName("Should throw when account number is null")
    void shouldThrowWhenAccountNumberIsNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new BankDetails(
                        null,
                        "123456789"
                )
        );
    }

    @Test
    @DisplayName("Should throw when account number is blank")
    void shouldThrowWhenAccountNumberIsBlank() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new BankDetails(
                        "   ",
                        "123456789"
                )
        );
    }

    @Test
    @DisplayName("Should throw when account number length is invalid")
    void shouldThrowWhenAccountNumberLengthIsInvalid() {

        assertThrows(
                InvalidAccountNumberException.class,
                () -> new BankDetails(
                        "123",
                        "123456789"
                )
        );
    }

    @Test
    @DisplayName("Should throw when ABA is null")
    void shouldThrowWhenAbaIsNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new BankDetails(
                        "123456789012",
                        null
                )
        );
    }

    @Test
    @DisplayName("Should throw when ABA is blank")
    void shouldThrowWhenAbaIsBlank() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new BankDetails(
                        "123456789012",
                        "   "
                )
        );
    }

    @Test
    @DisplayName("Should throw when ABA length is invalid")
    void shouldThrowWhenAbaLengthIsInvalid() {

        assertThrows(
                InvalidABAException.class,
                () -> new BankDetails(
                        "123456789012",
                        "123"
                )
        );
    }
}